package entities;



import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "domicilios")
public class Domicilio extends Base{


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
	@JsonBackReference
	private Cliente cliente;

	
}
