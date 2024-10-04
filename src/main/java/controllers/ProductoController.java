package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.ProductoDTO;
import entities.Producto;
import jakarta.validation.Valid;
import services.ProductoServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/productos")
public class ProductoController extends BaseControllerImpl<Producto, ProductoServiceImpl>{

	
	@PostMapping("/crearProducto")
    public ResponseEntity<?> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        try {
            Producto producto = servicio.crearProducto(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
}

