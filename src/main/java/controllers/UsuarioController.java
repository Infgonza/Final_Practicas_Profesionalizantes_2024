package controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.net.HttpStatus;

import dto.UsuarioDTO;
import entities.ERole;
import entities.RoleEntity;
import entities.Usuario;
import repositories.RoleRepository;
import repositories.UsuarioRepository;
import services.UsuarioServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/usuarios")
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioServiceImpl> {
	
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/listar")
	public ResponseEntity<?> getAllUsers() {
	    try {
	        List<UsuarioDTO> usuarios = servicio.findAll().stream()
	                .map(UsuarioDTO::fromEntity)
	                .collect(Collectors.toList());
	        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
	    }
	
	}
	
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            // Verificar si el usuario existe y obtenerlo
            Usuario usuario = servicio.findById(id);
            
            // Verificar si es el último administrador
            if (((UsuarioServiceImpl) servicio).isLastAdmin(usuario)) {
                return ResponseEntity.badRequest()
                    .body("{\"error\":\"No se puede eliminar el último administrador\"}");
            }
            
            // Usar el método delete heredado de BaseServiceImpl
            servicio.delete(id);
            return ResponseEntity.noContent().build();
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\":\"Error al eliminar el usuario: " + e.getMessage() + "\"}");
        }
    }
    
    @PutMapping("/cambiarRol/{id}")
    public ResponseEntity<?> cambiarRol(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            // Obtener usuario
            Usuario usuario = servicio.findById(id);
            if (usuario == null) {
                return ResponseEntity.badRequest()
                    .body("{\"error\":\"Usuario no encontrado\"}");
            }

            String nuevoRol = request.get("rol");
            if (nuevoRol == null || nuevoRol.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("{\"error\":\"Nuevo rol no especificado\"}");
            }

            // Verificar si es ultimo admin
            boolean esActualmenteAdmin = usuario.getRoles().stream()
                .anyMatch(role -> role.getNombreRol().equals(ERole.Administrador));
            
            if (esActualmenteAdmin && 
                nuevoRol.equals("Usuario") && 
                ((UsuarioServiceImpl) servicio).isLastAdmin(usuario)) {
                return ResponseEntity.badRequest()
                    .body("{\"error\":\"No se puede cambiar el rol del último administrador\"}");
            }
            
            // Encontrar nuevo rol
            RoleEntity newRole = roleRepository.findByNombreRol(
                nuevoRol.equals("Administrador") ? ERole.Administrador : ERole.Usuario
            ).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            // Verificar que rol no es nulo
            if (usuario.getRoles() == null) {
                usuario.setRoles(new HashSet<>());
            }

            // Quitar rol actuar y agregar el nuevo
            usuario.getRoles().clear();
            usuario.getRoles().add(newRole);
            usuario.setRol(nuevoRol);

            // Guardar usuario con nuevo rol
            Usuario usuarioActualizado = usuarioRepository.save(usuario);


            return ResponseEntity.ok(UsuarioDTO.fromEntity(usuarioActualizado));
            
        } catch (Exception e) {
           
            System.err.println("Error en cambiarRol:");
            e.printStackTrace();
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\":\"Error al cambiar el rol: " + 
                    (e.getMessage() != null ? e.getMessage() : "Error desconocido") + "\"}");
        }
    }
	

}
