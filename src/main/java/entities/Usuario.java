package entities;



import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long idDetalleCompra;
	
	@Column(name = "nombre_usuario", unique = true)
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
	
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
	@JoinTable(name="usuarios_roles", joinColumns = @JoinColumn(name="usuario_id"), inverseJoinColumns = @JoinColumn(name="rol_id"))
	private Set<RoleEntity> roles;
	

}

