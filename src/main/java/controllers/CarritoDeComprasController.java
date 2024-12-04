package controllers;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import entities.CarritoDeCompras;
import entities.Usuario;
import repositories.UsuarioRepository;
import services.CarritoDeComprasServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/carrito")
public class CarritoDeComprasController extends BaseControllerImpl<CarritoDeCompras, CarritoDeComprasServiceImpl>{
	
	 private static final Logger logger = LoggerFactory.getLogger(CarritoDeComprasController.class);
	
	 @Autowired
     private CarritoDeComprasServiceImpl carritoService;
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 

	 private Usuario obtenerUsuarioAutenticado() {
	     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
	         return null;
	     }

	     String nombreUsuario = authentication.getName(); // Extrae el nombre de usuario del token
	     return usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null); // Busca al usuario por nombre de usuario
	 }



	 @PostMapping("/agregar")
	 public ResponseEntity<?> agregarProductoAlCarrito(@RequestParam Long productoId, @RequestParam int cantidad) {
	     try {
	         Usuario usuario = obtenerUsuarioAutenticado();
	         if (usuario == null) {
	             return ResponseEntity.status(401).body("Usuario no autenticado");
	         }

	         carritoService.agregarProductoAlCarrito(usuario.getIdUsuario(), productoId, cantidad);
	         return ResponseEntity.ok("Producto agregado al carrito exitosamente");
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error al agregar producto al carrito: " + e.getMessage());
	     }
	 }

	 @GetMapping("/productos")
	 public ResponseEntity<?> obtenerProductosCarrito() {
	     try {
	         Usuario usuario = obtenerUsuarioAutenticado();
	         if (usuario == null) {
	             return ResponseEntity.status(401).body("Usuario no autenticado");
	         }

	         List<Map<String, Object>> productosCarrito = carritoService.obtenerProductosCarrito(usuario.getIdUsuario());
	         return ResponseEntity.ok(productosCarrito);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error al obtener productos del carrito: " + e.getMessage());
	     }
	 }

	 
	 
	 @DeleteMapping("/productos/{productoId}")
	    public ResponseEntity<?> eliminarProductoDelCarrito(Authentication authentication, @PathVariable Long productoId) {
	        try {
	            Usuario usuario = (Usuario) authentication.getPrincipal();
	            carritoService.eliminarProductoDelCarrito(usuario.getIdUsuario(), productoId);
	            return ResponseEntity.ok().body("Producto eliminado del carrito exitosamente");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error al eliminar producto del carrito: " + e.getMessage());
	        }
	    }
}
	

	
	    

	
