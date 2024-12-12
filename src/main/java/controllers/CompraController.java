package controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.CompraDTO;
import entities.Compra;
import services.CompraService;
import services.CompraServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/compras")
public class CompraController extends BaseControllerImpl<Compra, CompraServiceImpl> {


    @Autowired
    private CompraServiceImpl compraService;

    
    @PostMapping("/guardar")
    public ResponseEntity<Compra> guardarCompra(@RequestBody CompraDTO compraDTO) {
        try {
            Compra compra = compraService.guardarCompra(compraDTO);
            
            return ResponseEntity.ok(compra);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
