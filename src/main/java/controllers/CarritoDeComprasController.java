package controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
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
}

	
	    

	
