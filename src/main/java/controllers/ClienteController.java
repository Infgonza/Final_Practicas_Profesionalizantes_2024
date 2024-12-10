package controllers;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.ClienteRegistroDTO;
import entities.Cliente;
import entities.Usuario;
import repositories.UsuarioRepository;
import services.ClienteServiceImpl;
import services.JwtService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/clientes")
public class ClienteController extends BaseControllerImpl<Cliente, ClienteServiceImpl>{

	@Autowired
	private ClienteServiceImpl clienteService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	
	
	@PostMapping("/registro")
	public ResponseEntity<?> registrarCliente(@RequestBody ClienteRegistroDTO registroDTO, @RequestHeader("Authorization") String authorizationHeader) {
	    try {
	        // Extraer el token del encabezado Authorization
	        String token = authorizationHeader.replace("Bearer ", "");
	        String username = jwtService.extractUsername(token); // Obtén el username del token
	        
	        // Buscar el Usuario por el username
	        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        // Llamar al servicio para registrar el cliente, pasando el idUsuario
	        Cliente cliente = clienteService.registrarCliente(registroDTO, usuario.getIdUsuario());

	        return ResponseEntity.ok(cliente); // Retornar el cliente registrado
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Error al registrar cliente: " + e.getMessage());
	    }
	}
}
	