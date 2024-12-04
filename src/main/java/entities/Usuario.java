package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Usuario implements UserDetails, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long idUsuario;
	
	@Column(name = "nombre_usuario", unique = true)
	private String nombreUsuario;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "contrasenia")
	private String contrasenia;
	
	@Column(name = "rol")
	private String rol;

	// Relaciones
	
	@OneToOne(mappedBy = "usuario")
	@JsonBackReference
	private Cliente cliente;
	
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private CarritoDeCompras carrito;
	
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
	@JoinTable(name="usuarios_roles", joinColumns = @JoinColumn(name="usuario_id"), inverseJoinColumns = @JoinColumn(name="rol_id"))
	private Set<RoleEntity> roles;

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getNombreRol().name()))
            .collect(Collectors.toList());
    }

	 @Override
	    public String getPassword() {
	        return this.contrasenia;
	    }

	    @Override
	    public String getUsername() {
	        return this.nombreUsuario;
	    }
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true; 
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true; 
	    }

	    @Override
	    public boolean isEnabled() {
	        return true; 
	    }
	

}

