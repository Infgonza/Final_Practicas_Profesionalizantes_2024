package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.CarritoDeCompras;
import entities.ItemCarrito;
import entities.Producto;
import entities.Usuario;
import jakarta.transaction.Transactional;
import repositories.BaseRepository;
import repositories.CarritoDeComprasRepository;
import repositories.ProductoRepository;
import repositories.UsuarioRepository;

@Service
public class CarritoDeComprasServiceImpl extends BaseServiceImpl<CarritoDeCompras ,Long> implements CarritoDeComprasService{


	public CarritoDeComprasServiceImpl(BaseRepository<CarritoDeCompras, Long> baseRepository) {
		super(baseRepository);

	}
	
	@Autowired
    private CarritoDeComprasRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public void agregarProductoAlCarrito(Long usuarioId, Long productoId, int cantidad) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CarritoDeCompras carrito = usuario.getCarrito();
        if (carrito == null) {
            carrito = new CarritoDeCompras();
            carrito.setUsuario(usuario);
            usuario.setCarrito(carrito);
        }

        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ItemCarrito item = carrito.getItems().stream()
            .filter(i -> i.getProducto().getIdProducto().equals(productoId))
            .findFirst()
            .orElse(null);

        if (item == null) {
            item = new ItemCarrito();
            item.setProducto(producto);
            item.setCantidad(cantidad);
            item.setCarrito(carrito);
            carrito.getItems().add(item);
        } else {
            item.setCantidad(item.getCantidad() + cantidad);
        }

        carritoRepository.save(carrito);
    }
    
    public List<Map<String, Object>> obtenerProductosCarrito(Long usuarioId) {
        CarritoDeCompras carrito = carritoRepository.findByUsuarioIdUsuario(usuarioId)
            .orElseGet(() -> {
                // Si el carrito no existe, lo creamos para el usuario
                Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                CarritoDeCompras nuevoCarrito = new CarritoDeCompras();
                nuevoCarrito.setUsuario(usuario);
                carritoRepository.save(nuevoCarrito); // Guardamos el carrito nuevo en la base de datos
                return nuevoCarrito;
            });

        // Ahora, devolvemos los productos del carrito
        return carrito.getItems().stream()
            .map(item -> {
                Producto producto = item.getProducto();
                Map<String, Object> productoInfo = new HashMap<>();
                productoInfo.put("id", producto.getIdProducto());
                productoInfo.put("nombre", producto.getNombre());
                productoInfo.put("precio", producto.getPrecio());
                productoInfo.put("cantidad", item.getCantidad());
                productoInfo.put("imagenUrl", producto.getImagenUrl());
                return productoInfo;
            })
            .collect(Collectors.toList());
    }



    
    @Transactional
    public void eliminarProductoDelCarrito(Long usuarioId, Long productoId) {
        CarritoDeCompras carrito = carritoRepository.findByUsuarioIdUsuario(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        carrito.getItems().removeIf(item -> item.getProducto().getIdProducto().equals(productoId));
        carritoRepository.save(carrito);
    }
}

    		


    


	



