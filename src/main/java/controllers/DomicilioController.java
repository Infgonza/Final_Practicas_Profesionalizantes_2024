package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entities.Domicilio;
import services.DomicilioService;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/domicilios")
public class DomicilioController {

	@Autowired
	private DomicilioService domicilioService;
	
	@GetMapping("")
	public ResponseEntity<?> getAll(){
		try {
			
			// Si se se encuentra en nuestra base de datos Clientes se va devolver una lista de Clientes
			return ResponseEntity.status(HttpStatus.OK).body(domicilioService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\""
					+ "Error. Por favor intente más tarde.\"}");
			
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(domicilioService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\""
					+ "Error. Por favor intente más tarde.\"}");
			
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> save(@RequestBody Domicilio entity){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(domicilioService.save(entity));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\""
					+ "Error. Por favor intente más tarde.\"}");
			
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Domicilio entity){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(domicilioService.update(id, entity));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\""
					+ "Error. Por favor intente más tarde.\"}");
			
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(domicilioService.delete(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\""
					+ "Error. Por favor intente más tarde.\"}");
			
		}
	}
}
