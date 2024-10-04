package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dto.LoginDTO;
import dto.RegistroDTO;
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
		
		Usuario nuevoUsuario = Usuario.builder()
				.nombreUsuario(registroDTO.getNombreUsuario())
				.email(registroDTO.getEmail())
                .contrasenia(passwordEncoder.encode(registroDTO.getContrasenia()))
                .rol("Usuario")
                .build();
		return usuarioRepository.save(nuevoUsuario);		
	}
	public String login(LoginDTO loginDTO) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!passwordEncoder.matches(loginDTO.getContrasenia(), usuario.getContrasenia())) {
            throw new Exception("Credenciales inválidas");
        }

        return jwtService.generateToken(usuario);
    }
}


