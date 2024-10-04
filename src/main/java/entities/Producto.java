package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="producto")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_producto", discriminatorType = DiscriminatorType.STRING)
public class Producto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name="id_producto")
	private Long idProducto;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="descripción")
	private String descripción;
	
	@Column(name="precio")
	private double precio;
	
	@Column(name="stock")
	private int stock;
	
	@Column(name="imagen_url")
	private String imagenUrl;
	
	// RELACIONES
	
	@OneToMany(mappedBy = "producto")
	private List<DetalleCompra> detallesCompra = new ArrayList<DetalleCompra>();
	
	@OneToMany(mappedBy="producto")
	private List<DetalleFactura> detallesFactura = new ArrayList<DetalleFactura>();
	
	@ManyToMany(mappedBy="productos")
	private List<CarritoDeCompras> carrito = new ArrayList<CarritoDeCompras>();

	

}
