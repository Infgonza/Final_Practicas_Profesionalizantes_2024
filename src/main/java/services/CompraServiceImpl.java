package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.CompraDTO;
import dto.ProductoDTO;
import entities.CarritoDeCompras;
import entities.Cliente;
import entities.Compra;
import entities.DetalleCompra;
import entities.Producto;
import entities.Usuario;
import repositories.BaseRepository;
import repositories.CarritoDeComprasRepository;
import repositories.ClienteRepository;
import repositories.CompraRepository;
import repositories.DetalleCompraRepository;
import repositories.ProductoRepository;


@Service
public class CompraServiceImpl extends BaseServiceImpl<Compra, Long> implements CompraService{

	@Autowired
	private CompraRepository compraRepository;
	

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CarritoDeComprasRepository carritoDeComprasRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

	
	public CompraServiceImpl(BaseRepository<Compra, Long> baseRepository) {
		super(baseRepository);
	}

	
    public Compra guardarCompra(CompraDTO compraDTO) {
    	 List<DetalleCompra> detallesCompra = new ArrayList<>(); 
        // OBTENEMOS EL ULTIMO ID DE DE CLIENTE
        Cliente ultimoCliente = clienteRepository.findTopByOrderByIdClienteDesc();
        if (ultimoCliente == null) {
            throw new RuntimeException("No se encontro ningun cliente registrado.");
        }
        
        // CON EL ID DEL ULTIMO CLIENTE OBTENEMOS EL ID DE USUARIO QUE CONTIENE
        Long usuarioId = ultimoCliente.getUsuario().getIdUsuario();
        if (usuarioId == null) {
            throw new RuntimeException("El cliente no tiene un usuario relacionado.");
        }


        // Y CON EL ID DE USUARIO BUSCAMOS EL ID DEL CARRITO RELACIONADO
        
        Optional<CarritoDeCompras> carrito = carritoDeComprasRepository.findByUsuarioIdUsuario(usuarioId);
        CarritoDeCompras carritoDeCompras = carrito.orElseThrow(() -> new RuntimeException("No se encontro un carrito para el usuario con ID: " + usuarioId));

        

        
        // CREAMOS Y GUARDAMOS LA COMPRA
        Compra compra = new Compra();
        compra.setCliente(ultimoCliente);
        compra.setCarrito(carritoDeCompras);
        compra.setEstado("aprobado");
        compra.setTotal(compraDTO.getTotal()); 
        
        /*
        for (ProductoDTO productoDTO : compraDTO.getProductos()) {
            // OBTENEMOS EL PRODUCTO POR SU ID
            Producto producto = productoRepository.findById(productoDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            
            DetalleCompra detalleCompra = new DetalleCompra();
            detalleCompra.setProducto(producto);
            detalleCompra.setCantidad(productoDTO.getCantidad());
            detalleCompra.setPrecioUnitario(productoDTO.getPrecio());
            
            detalleCompra.setCompra(compra);
            detallesCompra.add(detalleCompra);
        }
        
        
        compra.setDetallesCompra(detallesCompra);
        detalleCompraRepository.saveAll(detallesCompra);*/
        return compraRepository.save(compra);
    }
}
