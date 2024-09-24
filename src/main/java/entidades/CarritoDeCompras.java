package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
@Table(name="carrito_de_compra")
public class CarritoDeCompras implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_carrito_de_compra")
	private Long idCarritoDeCompra;
	
	@Column(name="subtotal")
	private double subtotal;
	
	@Column(name="precio_unitario")
	private double precioUnitario;
	
	// RELACIONES
	
	// Un Cliente tiene un Carrito de Compras
	@OneToOne(mappedBy = "carrito")
	private Cliente cliente;
	
	// Un carrito va a representar una Compra
	@OneToOne(mappedBy = "carrito")
	private Compra compra;
	
	// Un Carrito puede tener muchos Productos
	@ManyToMany( cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			name="carrito_producto",
			joinColumns= @JoinColumn (name=" id_carrito"),
			inverseJoinColumns = @JoinColumn(name="id_producto"))
	private List<Producto> productos = new ArrayList<Producto>();
	
	
}
