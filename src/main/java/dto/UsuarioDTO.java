package dto;

import java.util.Set;
import java.util.stream.Collectors;

import entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UsuarioDTO {
	    
	    private Long id;
	    private String nombreUsuario;
	    private String email;
	    private String rol;
	 
	    private Set<String> roles;
	    private String telefono;
	    
	    public static UsuarioDTO fromEntity(Usuario usuario) {
	        String rolString = usuario.getRoles().stream()
	            .map(role -> role.getNombreRol().toString())
	            .findFirst()
	            .orElse("Usuario"); 
	        
	        return new UsuarioDTO(
	            usuario.getIdUsuario(),
	            usuario.getNombreUsuario(),
	            
	            usuario.getEmail(),
	            
	            
	            rolString,
	            usuario.getRoles().stream()
	                .map(role -> role.getNombreRol().toString())
	                .collect(Collectors.toSet()),
	                usuario.getTelefono()
	        );
	       
	    }
}
