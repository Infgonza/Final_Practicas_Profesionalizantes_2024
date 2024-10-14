package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.ItemCarritoDTO;
import entities.CarritoDeCompras;
import entities.Usuario;
import services.CarritoDeComprasServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/carrito")
public class CarritoDeComprasController extends BaseControllerImpl<CarritoDeCompras, CarritoDeComprasServiceImpl>{
	
	 @Autowired
	    private CarritoDeComprasServiceImpl carritoService;

	    @GetMapping("/")
	    public ResponseEntity<?> getCarrito(Authentication authentication) {
	        Usuario usuario = (Usuario) authentication.getPrincipal();
	        CarritoDeCompras carrito = carritoService.getOrCreateCarritoForUser(usuario);
	        return ResponseEntity.ok(carrito);
	    }

	    @PostMapping("/agregar")
	    public ResponseEntity<?> agregarAlCarrito(@RequestBody ItemCarritoDTO itemDTO, Authentication authentication) {
	        Usuario usuario = (Usuario) authentication.getPrincipal();
	        try {
	            carritoService.addItemToCarrito(usuario, itemDTO.getProductoId(), itemDTO.getCantidad());
	            return ResponseEntity.ok().build();
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }

	    @PostMapping("/actualizar")
	    public ResponseEntity<?> actualizarCantidad(@RequestBody ItemCarritoDTO itemDTO, Authentication authentication) {
	        Usuario usuario = (Usuario) authentication.getPrincipal();
	        try {
	            carritoService.updateItemQuantity(usuario, itemDTO.getProductoId(), itemDTO.getCantidad());
	            return ResponseEntity.ok().build();
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }

	    @DeleteMapping("/eliminar/{productoId}")
	    public ResponseEntity<?> eliminarDelCarrito(@PathVariable Long productoId, Authentication authentication) {
	        Usuario usuario = (Usuario) authentication.getPrincipal();
	        try {
	            carritoService.removeItemFromCarrito(usuario, productoId);
	            return ResponseEntity.ok().build();
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }
	}

	
	    

	
