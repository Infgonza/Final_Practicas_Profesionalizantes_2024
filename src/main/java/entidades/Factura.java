package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="factura")
public class Factura implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name="id_factura")
	private Long idFactura;
	
	@Column(name="fecha_emision")
	private LocalDate fechaEmision;
	
	@Column(name="monto_total")
	private double montoTotal;
	
	
	// Relaciones
	
	// Una Compra genera una Factura
	@OneToOne(mappedBy = "factura")
	private Compra compra;	
	
	// Un Cliente va a tener una Factura
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name= "fk_cliente")
	private Cliente cliente;
	
	//Una Factura genera muchos detalles factura Detalle Factura
	@OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DetalleFactura> detallesFactura = new ArrayList<DetalleFactura>();
	
	

}
