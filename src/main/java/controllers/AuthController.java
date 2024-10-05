package controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.LoginDTO;
import dto.RegistroDTO;
import entities.Usuario;
import services.UsuarioServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/auth")
public class AuthController extends BaseControllerImpl<Usuario, UsuarioServiceImpl> {
	
	

    @PostMapping("registro")
    public ResponseEntity<?> registro(@RequestBody RegistroDTO registroDTO) throws Exception {
        
            Usuario nuevoUsuario = servicio.registrar(registroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
       
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) throws Exception {
       
            String token = servicio.login(loginDTO);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
       
    }
}