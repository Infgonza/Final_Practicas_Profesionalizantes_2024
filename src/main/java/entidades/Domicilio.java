package entidades;

import java.io.Serializable;

import javax.persistence.*;

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
@Table(name = "domicilios")
public class Domicilio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_domicilio")
	private Long idDomicilio;
	
	@Column(name = "calle")
	private String calle;
	
	@Column(name = "numero")
	private int numero;
	
	@Column(name = "cod_postal")
	private int codPostal;
	
	@Column(name = "provincia")
	private String provincia;
	
	// Relaciones
	@OneToOne(mappedBy = "domicilio")
	private Cliente cliente;

	
}
