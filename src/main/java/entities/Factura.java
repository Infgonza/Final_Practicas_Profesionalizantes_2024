package entities;

import java.io.Serializable;
import java.time.LocalDate;
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
