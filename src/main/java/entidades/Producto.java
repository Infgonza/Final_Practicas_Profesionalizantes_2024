package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	// RELACIONES
	
	@OneToMany(mappedBy = "producto")
	private List<DetalleCompra> detallesCompra = new ArrayList<DetalleCompra>();
	
	@OneToMany(mappedBy="producto")
	private List<DetalleFactura> detallesFactura = new ArrayList<DetalleFactura>();
	
	@ManyToMany(mappedBy="productos")
	private List<CarritoDeCompras> carrito = new ArrayList<CarritoDeCompras>();

	

}
