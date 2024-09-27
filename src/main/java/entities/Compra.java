package entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "compra")
public class Compra implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name="id_compra")
	private Long idCompra;
	
	@Column(name = "total")
	private double total;
	
	@Column(name = "estado")
	private String estado;

	
	// Relaciones
	
	// Muchas Compras va tener un Clinente
	@ManyToOne
	@JoinColumn(name= "fk_cliente")
	private Cliente cliente;
	
	// Una Compra va a generar una Factura
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="fk_factura")
	private Factura factura;
	
	// Una Compra va a tener un Carrito de Compras
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="fk_carrito")
	private CarritoDeCompras carrito;
	

	// Una Compra va a generar muchos DetalleCompra
	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DetalleCompra> detallesCompra = new ArrayList<DetalleCompra>();
	

}
