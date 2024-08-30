package entidades;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")	
	private Long id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido")
	private String apellido;
	
	
}
