package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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


@Getter @Setter
@Builder @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cliente")
	private Long idDetalleCompra;
	
	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;

	@Column(name = "dni", unique = true)
	private int dni;

	// RELACIONES

	// Un Cliente tiene un Domicilio
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_domicilio")
	@JsonManagedReference
	private Domicilio domicilio;

	// Un Cliente tiene un usuario
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_usuario")
	@JsonManagedReference
	private Usuario usuario;
	
	// Un Cliente tiene una Factura al realizar un compra
	@OneToMany(mappedBy= "cliente")
	private List<Factura> factura = new ArrayList<Factura>();
	
	// Un Cliente puede tener 1 o más compras
	@OneToMany(mappedBy = "cliente")
	private List<Compra> compra = new ArrayList<Compra>();
	
	// Un cliente tiene un Carrito de Compras
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="fk_carrito")
	private CarritoDeCompras carrito;


	
   
}
