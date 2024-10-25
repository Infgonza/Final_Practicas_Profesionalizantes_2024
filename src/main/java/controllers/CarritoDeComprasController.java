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
import services.CarritoDeComprasServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/carrito")
public class CarritoDeComprasController extends BaseControllerImpl<CarritoDeCompras, CarritoDeComprasServiceImpl>{
	
	 private static final Logger logger = LoggerFactory.getLogger(CarritoDeComprasController.class);
	
	 @Autowired
     private CarritoDeComprasServiceImpl carritoService;

	 @PostMapping("/agregar")
	    public ResponseEntity<?> agregarProductoAlCarrito(
	            Authentication authentication,
	            @RequestParam Long productoId,
	            @RequestParam int cantidad) {
	        logger.info("Recibida solicitud para agregar producto al carrito. ProductoId: {}, Cantidad: {}", productoId, cantidad);
	        try {
	            Usuario usuario = (Usuario) authentication.getPrincipal();
	            logger.info("Usuario autenticado: {}", usuario.getNombreUsuario());
	            carritoService.agregarProductoAlCarrito(usuario.getIdUsuario(), productoId, cantidad);
	            logger.info("Producto agregado al carrito exitosamente");
	            return ResponseEntity.ok().body("Producto agregado al carrito exitosamente");
	        } catch (Exception e) {
	            logger.error("Error al agregar producto al carrito", e);
	            return ResponseEntity.badRequest().body("Error al agregar producto al carrito: " + e.getMessage());
	        }
	 }
	 
	 @GetMapping("/productos")
	    public ResponseEntity<?> obtenerProductosCarrito() {
	        logger.info("Recibida solicitud para obtener productos del carrito");
	        try {
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	            if (authentication == null || !authentication.isAuthenticated()) {
	                logger.error("Usuario no autenticado");
	                return ResponseEntity.status(401).body("Usuario no autenticado");
	            }
	            
	            Object principal = authentication.getPrincipal();
	            if (!(principal instanceof Usuario)) {
	                logger.error("Principal no es una instancia de Usuario");
	                return ResponseEntity.status(401).body("Tipo de autenticación no válido");
	            }
	            
	            Usuario usuario = (Usuario) principal;
	            logger.info("Usuario autenticado: {}", usuario.getNombreUsuario());
	            
	            List<Map<String, Object>> productosCarrito = carritoService.obtenerProductosCarrito(usuario.getIdUsuario());
	            logger.info("Productos del carrito obtenidos exitosamente");
	            return ResponseEntity.ok().body(productosCarrito);
	        } catch (Exception e) {
	            logger.error("Error al obtener productos del carrito", e);
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
	

	
	    

	
