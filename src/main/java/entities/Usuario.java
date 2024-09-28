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
@Table(name = "usuarios")
public class Usuario extends Base {
	

	@Column(name = "nombre_usuario")
	private String nombreUsuario;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "contrasenia")
	private String contrasenia;
	
	@Column(name = "rol")
	private String rol;

	// Relaciones
	
	@OneToOne(mappedBy = "usuario")
	@JsonBackReference
	private Cliente cliente;

	

}

