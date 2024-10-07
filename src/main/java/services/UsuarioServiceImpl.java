package services;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dto.RegistroDTO;
import entities.ERole;
import entities.RoleEntity;
import entities.Usuario;
import repositories.BaseRepository;
import repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService{

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
		
		if(usuarioRepository.findByEmail(registroDTO.getEmail()).isPresent()) {
			throw new Exception("El email ya esta registrado");
		}
		if(usuarioRepository.findByNombreUsuario(registroDTO.getNombreUsuario()).isPresent()) {
			throw new Exception("El nombre de usuario ya está en uso");
		}
		
		  Set<RoleEntity> roles = new HashSet<>();
	        if (registroDTO.getRoles() != null && !registroDTO.getRoles().isEmpty()) {
	            roles = registroDTO.getRoles().stream()
	                .map(role -> RoleEntity.builder()
	                    .nombreRol(ERole.valueOf(role))
	                    .build())
	                .collect(Collectors.toSet());
	        } else {
	            // Si no se proporcionan roles, asignar el rol de Usuario por defecto
	            roles.add(RoleEntity.builder().nombreRol(ERole.Usuario).build());
	        }
		
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



