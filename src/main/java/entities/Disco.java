package entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("Disco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Disco extends Producto implements Serializable{

	private static final long serialVersionUID = 1L;
	

	@Column(name="artista")
	private String artista;
	@Column(name="genero")
	private String genero;
	@Column(name="fecha_lanzamiento")
	private String fechaLanzamiento;
	public Disco(Long idProducto, String nombre, String descripción, double precio, int stock, String imagen,
			List<DetalleCompra> detallesCompra, List<DetalleFactura> detallesFactura, List<CarritoDeCompras> carrito) {
		super();
	}
	public Disco(Long idProducto, String nombre, String descripción, double precio, int stock, String imagen,
			List<DetalleCompra> detallesCompra, List<DetalleFactura> detallesFactura, List<CarritoDeCompras> carrito,
			String artista, String genero, String fechaLanzamiento) {
		super();
		this.artista = artista;
		this.genero = genero;
		this.fechaLanzamiento = fechaLanzamiento;
	}
	
	
	
}
