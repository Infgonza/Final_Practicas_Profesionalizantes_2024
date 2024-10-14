package services;

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

}
