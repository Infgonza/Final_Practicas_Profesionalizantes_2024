package dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductoDTO {

	// Datos producto
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private MultipartFile imagenUrl;
	
	private String tipo; // Disco o Vinilo
	
	// Datos Disco o Vinilo
	private String Artista;
	private String genero;
	private String fechaLanzamiento;
	
}
