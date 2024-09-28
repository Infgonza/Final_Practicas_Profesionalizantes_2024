package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.ClienteRegistroDTO;
import entities.Cliente;
import entities.Domicilio;
import entities.Usuario;
import jakarta.transaction.Transactional;
import repositories.BaseRepository;
import repositories.ClienteRepository;
import repositories.DomicilioRepository;
import repositories.UsuarioRepository;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, Long> implements ClienteService{

	@Autowired
	private ClienteRepository clienteRepository;
    @Autowired
    private DomicilioRepository domicilioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

	public ClienteServiceImpl(BaseRepository<Cliente, Long> baseRepository) {
		super(baseRepository);
		
	}

	@Transactional
    public Cliente registrarCliente(ClienteRegistroDTO registroDTO) {
        // Crear y guardar Domicilio
        Domicilio domicilio = Domicilio.builder()
                .calle(registroDTO.getCalle())
                .numero(registroDTO.getNumero())
                .codPostal(registroDTO.getCodPostal())
                .provincia(registroDTO.getProvincia())
                .build();
        domicilio = domicilioRepository.save(domicilio);

        // Crear y guardar Usuario
        Usuario usuario = Usuario.builder()
                .nombreUsuario(registroDTO.getNombreUsuario())
                .email(registroDTO.getEmail())
                .contrasenia(registroDTO.getContrasenia()) // Considera encriptar la contraseña
                .rol(registroDTO.getRol())
                .build();
        usuario = usuarioRepository.save(usuario);

        // Crear y guardar Cliente
        Cliente cliente = Cliente.builder()
                .nombre(registroDTO.getNombre())
                .apellido(registroDTO.getApellido())
                .dni(registroDTO.getDni())
                .domicilio(domicilio)
                .usuario(usuario)
                .build();
        cliente = clienteRepository.save(cliente);

        return cliente;
    }
	


}
