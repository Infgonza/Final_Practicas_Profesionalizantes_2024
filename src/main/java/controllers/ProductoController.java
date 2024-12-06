package controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.ProductoDTO;
import entities.Producto;
import services.ProductoServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/productos")
public class ProductoController extends BaseControllerImpl<Producto, ProductoServiceImpl> {
	
	
	@GetMapping("/verificarPermisoSubir")
	public ResponseEntity<?> verificarPermisoSubirProducto() {
	        // Si el usuario llega aca, tiene los permisos necesarios
	        return ResponseEntity.ok().body("Permiso concedido");
	    }

    @PostMapping("/crearProducto")
    public ResponseEntity<?> crearProducto(@ModelAttribute ProductoDTO productoDTO) {
    	System.out.println("Recibida petición para crear producto: " + productoDTO);
        try {
            Producto producto = servicio.crearProducto(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(producto);
        } catch (Exception e) {
        	System.out.println("Error al crear producto");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> obtenerProductos() {
        try {
            List<ProductoDTO> productosDTO = servicio.obtenerTodosLosProductosComoDTO();
            return ResponseEntity.ok(productosDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener productos");
        }
    }

    @Override
    @GetMapping("/listarProductos")
    public ResponseEntity<?> getAll() {
        try {
             
            List<ProductoDTO> productosDTO = servicio.obtenerTodosLosProductosComoDTO();
            return ResponseEntity.ok(productosDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener productos");
        }
    }

    
    @Override
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
       
            servicio.eliminarProducto(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }
    
}