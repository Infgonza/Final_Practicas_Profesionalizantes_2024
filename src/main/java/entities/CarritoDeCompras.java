package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name="carrito_de_compra")
public class CarritoDeCompras implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_carrito_de_compra")
	private Long idCarritoDeCompra;
	

	
	// RELACIONES
	
	// Un Cliente tiene un Carrito de Compras
	@OneToOne
	@JoinColumn(name = "usuario_id", unique = true)
	private Usuario usuario;
	
	
	@OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemCarrito> items = new ArrayList<ItemCarrito>();

	 
	@OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
	private List<Compra> compra;
	
	
}
