package controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.LoginDTO;
import dto.RegistroDTO;
import entities.Usuario;
import services.JwtService;
import services.UsuarioServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/auth")
public class AuthController extends BaseControllerImpl<Usuario, UsuarioServiceImpl> {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Creacion de un usuario
    @PostMapping("registro")
    public ResponseEntity<?> registro(@RequestBody RegistroDTO registroDTO) throws Exception {
        logger.info("Intento de registro para usuario: {}", registroDTO.getNombreUsuario());
        try {
            Usuario nuevoUsuario = servicio.registrar(registroDTO);
            logger.info("Usuario registrado exitosamente: {}", nuevoUsuario.getNombreUsuario());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            logger.error("Error durante el registro: ", e);
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error durante el registro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // Creacion de un usuario ADMINISTRADOR mediante otro administrador
    @PostMapping("registro/admin")
    @PreAuthorize("hasRole('Administrador')") // Solo otros admins pueden crear admins
    public ResponseEntity<?> registroAdmin(@RequestBody RegistroDTO registroDTO) throws Exception {
        logger.info("Intento de registro de administrador: {}", registroDTO.getNombreUsuario());
        try {
            Usuario nuevoAdmin = servicio.crearUsuarioAdmin(registroDTO);
            logger.info("Administrador registrado exitosamente: {}", nuevoAdmin.getNombreUsuario());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAdmin);
        } catch (Exception e) {
            logger.error("Error durante el registro de administrador: ", e);
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error durante el registro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        logger.info("Intento de login para usuario: {}", loginDTO.getNombreUsuario());
        try {
            // Primero, verificamos si el usuario existe
            if (!servicio.existeUsuario(loginDTO.getNombreUsuario())) {
                logger.warn("Intento de login con usuario no existente: {}", loginDTO.getNombreUsuario());
                throw new BadCredentialsException("Usuario no encontrado");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getNombreUsuario(), loginDTO.getContrasenia())
            );
            
            UserDetails usuario = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(usuario);
            
            logger.info("Login exitoso para usuario: {}", usuario.getUsername());
            
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            logger.warn("Credenciales inválidas para usuario: {}", loginDTO.getNombreUsuario());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            logger.error("Error durante el login: ", e);
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error durante el login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}