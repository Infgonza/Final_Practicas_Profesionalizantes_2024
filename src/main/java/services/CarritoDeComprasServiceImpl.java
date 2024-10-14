package services;

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

    		
}

    


	



