package services;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dto.RegistroDTO;
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



