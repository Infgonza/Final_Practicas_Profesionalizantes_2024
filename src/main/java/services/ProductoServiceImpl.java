package services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.ProductoDTO;
import entities.Disco;
import entities.Producto;
import entities.Vinilo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import repositories.BaseRepository;
import repositories.DiscoRepository;
import repositories.ProductoRepository;
import repositories.ViniloRepository;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto, Long> implements ProductoService{
	
	@Autowired
    private ProductoRepository productoRepository;
	
	@Autowired
	private DiscoRepository discoRepository;
	
	@Autowired
	private ViniloRepository viniloRepository;
	
	@Autowired
	private S3Service s3Service;
	
	
	
	public List<ProductoDTO> obtenerTodosLosProductosComoDTO() throws Exception {
        List<Producto> productos = findAll();
        return productos.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public void eliminarProducto(Long id) throws Exception {
        
        delete(id);
    }

    private ProductoDTO convertToDTO(Producto producto) {
    	ProductoDTO dto = new ProductoDTO();
    	 
        
        // Mapea los campos de la entidad al DTO
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripción());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setImagenUrlString(producto.getImagenUrl());
         
        
        // Campos específicos para Disco o Vinilo
        if (producto instanceof Disco disco) {
            dto.setTipo("DISCO");
            dto.setArtista(disco.getArtista());
            dto.setGenero(disco.getGenero());
            dto.setFechaLanzamiento(disco.getFechaLanzamiento());
            dto.setImagenUrl(null);  
        } else if (producto instanceof Vinilo vinilo) {
            dto.setTipo("VINILO");
            dto.setArtista(vinilo.getArtista());
            dto.setGenero(vinilo.getGenero());
            dto.setFechaLanzamiento(vinilo.getFechaLanzamiento());
            dto.setImagenUrl(null);  
        }
        
        return dto;
    }
	
	public ProductoServiceImpl(BaseRepository<Producto, Long> baseRepository) {
		super(baseRepository);
	}
	
	@Transactional
	public Producto crearProducto(ProductoDTO productoDTO) {
		
		String imagenUrl = s3Service.uploadFile(productoDTO.getImagenUrl());
		
		if("DISCO".equalsIgnoreCase(productoDTO.getTipo())) {
			Disco disco = new Disco();
			
			// Datos de Producto
			disco.setNombre(productoDTO.getNombre());
			disco.setDescripción(productoDTO.getDescripcion());
			disco.setPrecio(productoDTO.getPrecio());
			disco.setStock(productoDTO.getStock());
			disco.setImagenUrl(imagenUrl);
			
			// Datos de Disco
			disco.setArtista(productoDTO.getArtista());
			disco.setGenero(productoDTO.getGenero());
			disco.setFechaLanzamiento(productoDTO.getFechaLanzamiento());
			
			disco = discoRepository.save(disco);
			return disco;
			
		} else if ("VINILO".equalsIgnoreCase(productoDTO.getTipo())){
			Vinilo vinilo = new Vinilo();
			
			// Datos de producto
			vinilo.setNombre(productoDTO.getNombre());
			vinilo.setDescripción(productoDTO.getDescripcion());
			vinilo.setPrecio(productoDTO.getPrecio());
			vinilo.setStock(productoDTO.getStock());
			vinilo.setImagenUrl(imagenUrl);
			
			// Datos de vinilo
			vinilo.setArtista(productoDTO.getArtista());
			vinilo.setGenero(productoDTO.getGenero());
			vinilo.setFechaLanzamiento(productoDTO.getFechaLanzamiento());

			vinilo = viniloRepository.save(vinilo);
			return vinilo;
			
		}  else {
            throw new IllegalArgumentException("Tipo de producto no válido: " + productoDTO.getTipo());
        }
		

    }
	 public Producto obtenerProductoPorId(Long id) {
	        return productoRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + id));
	}

	@Transactional
	public void descontarStock(Long idProducto, int stock) throws Exception {
	    // OBTENEMOS EL PRODUCTO POR ID
	    Producto producto = productoRepository.findById(idProducto)
	        .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + idProducto));
	     
	    // VERIFICAMOS QUE EL STOCK SEA SUFICIENTE
	    if (producto.getStock() < stock) {
	        throw new IllegalArgumentException("Stock insuficiente para el producto con id: " + idProducto);
	    }
	     
	    // Y DESCONTAMOS DEL STOCK
	    producto.setStock(producto.getStock() - stock);
	     
	    productoRepository.save(producto);
	}
	 
}
