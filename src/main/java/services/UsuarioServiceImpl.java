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
	    // Verificar si el email ya está registrado
	    if (usuarioRepository.findByEmail(registroDTO.getEmail()).isPresent()) {
	        throw new Exception("El email ya está registrado");
	    }
	    // Verificar si el nombre de usuario ya está en uso
	    if (usuarioRepository.findByNombreUsuario(registroDTO.getNombreUsuario()).isPresent()) {
	        throw new Exception("El nombre de usuario ya está en uso");
	    }

	    Set<RoleEntity> roles = new HashSet<>();
	    if (registroDTO.getRoles() != null && !registroDTO.getRoles().isEmpty()) {
	        for (String role : registroDTO.getRoles()) {
	            // Buscar el rol en la base de datos
	            RoleEntity rolExistente = roleRepository.findByNombreRol(ERole.valueOf(role))
	                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado: " + role));
	            roles.add(rolExistente);
	        }
	    } else {
	        // Asignar el rol de Usuario por defecto
	        RoleEntity rolUsuario = roleRepository.findByNombreRol(ERole.Usuario)
	                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado: Usuario"));
	        roles.add(rolUsuario);
	    }

	    // Crear nuevo usuario
	    Usuario nuevoUsuario = Usuario.builder()
	            .nombreUsuario(registroDTO.getNombreUsuario())
	            .email(registroDTO.getEmail())
	            .contrasenia(passwordEncoder.encode(registroDTO.getContrasenia()))
	            .roles(roles)
	            .build();

	    return usuarioRepository.save(nuevoUsuario);
	}

	
	public boolean existeUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario).isPresent();
    }
	
	  
	
}



