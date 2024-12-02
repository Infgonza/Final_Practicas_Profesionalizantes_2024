package services;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mercadopago.net.HttpStatus;

import dto.RegistroDTO;
import dto.UsuarioDTO;
import entities.ERole;
import entities.RoleEntity;
import entities.Usuario;
import repositories.BaseRepository;
import repositories.RoleRepository;
import repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;

	public UsuarioServiceImpl(BaseRepository<Usuario, Long> baseRepository) {
		super(baseRepository);
	}
	
	public ResponseEntity<?> eliminarUsuario(Long idUsuario) {
        try {
            // Verificar si el usuario existe
            Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new Exception("No existe usuario con el ID: " + idUsuario));
            
            // Convertir a DTO para verificación antes de eliminar
            UsuarioDTO usuarioDTO = UsuarioDTO.fromEntity(usuario);
            
            // Verificar si es el último administrador
            if (isLastAdmin(usuario)) {
                return ResponseEntity.badRequest()
                    .body("{\"error\":\"No se puede eliminar el último administrador del sistema\"}");
            }
            
            // Proceder con la eliminación
            usuarioRepository.delete(usuario);
            
            return ResponseEntity.noContent().build();
                
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\":\"Error al eliminar el usuario: " + e.getMessage() + "\"}");
        }
    }
    
    public boolean isLastAdmin(Usuario usuario) {
        // Verificar si el usuario es admin
        boolean isAdmin = usuario.getRoles().stream()
            .anyMatch(role -> role.getNombreRol().equals(ERole.Administrador));
            
        if (!isAdmin) {
            return false;
        }
        
        // Contar cuántos admins hay en total
        long adminCount = usuarioRepository.findAll().stream()
            .filter(u -> u.getRoles().stream()
                .anyMatch(role -> role.getNombreRol().equals(ERole.Administrador)))
            .count();
                
        return adminCount <= 1;
    }

	
	public Usuario registrar(RegistroDTO registroDTO) throws Exception {
		
	    // Verificamos si el email ya esta registrado
	    if (usuarioRepository.findByEmail(registroDTO.getEmail()).isPresent()) {
	        throw new Exception("El email ya está registrado");
	    }
	    
	    // Verificamos si el nombre de usuario ya esta en uso
	    if (usuarioRepository.findByNombreUsuario(registroDTO.getNombreUsuario()).isPresent()) {
	        throw new Exception("El nombre de usuario ya está en uso");
	    }

	    // Creamos el nuevo usuario
	    Usuario usuario = Usuario.builder()
                .nombreUsuario(registroDTO.getNombreUsuario())
                .email(registroDTO.getEmail())
                .contrasenia(passwordEncoder.encode(registroDTO.getContrasenia()))
                .build();
	    
	    
	    // Asignamos al nuevo usuario el rol por defecto USUARIO
	    Set<RoleEntity> roles = new HashSet<>();
        RoleEntity userRole = roleRepository.findByNombreRol(ERole.Usuario)
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
        roles.add(userRole);
        
        usuario.setRoles(roles);
        
        return usuarioRepository.save(usuario);
    }
	    
	public Usuario crearUsuarioAdmin(RegistroDTO registroDTO) throws Exception {
		
		// Verificamos si el email ya esta registrado
	    if (usuarioRepository.findByEmail(registroDTO.getEmail()).isPresent()) {
	        throw new Exception("El email ya está registrado");
	    }
	    
	    // Verificamos si el nombre de usuario ya esta en uso
	    if (usuarioRepository.findByNombreUsuario(registroDTO.getNombreUsuario()).isPresent()) {
	        throw new Exception("El nombre de usuario ya está en uso");
	    }

        // Creamos un nuevo usuario 
        Usuario admin = Usuario.builder()
                .nombreUsuario(registroDTO.getNombreUsuario())
                .email(registroDTO.getEmail())
                .contrasenia(passwordEncoder.encode(registroDTO.getContrasenia()))
                .build();

        // Y le asignamos rol de administrador
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity adminRole = roleRepository.findByNombreRol(ERole.Administrador)
                .orElseThrow(() -> new RuntimeException("Error: Rol de administrador no encontrado."));
        roles.add(adminRole);
        
        admin.setRoles(roles);
        
        return usuarioRepository.save(admin);
    }
	
	public boolean existeUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario).isPresent();
    }

}



