package dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ProductoDTO {
	
	@JsonIgnore
    private MultipartFile imagenUrl;
	private String imagenUrlString;

	// Datos producto
	private Long idProducto;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	
	
	private String tipo; // Disco o Vinilo
	
	// Datos Disco o Vinilo
	private String artista;
	private String genero;
	private String fechaLanzamiento;
	
}
