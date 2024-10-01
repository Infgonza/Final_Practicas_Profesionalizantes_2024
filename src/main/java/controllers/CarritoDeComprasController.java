package controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entities.CarritoDeCompras;
import services.CarritoDeComprasServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/carrito_de_compras")
public class CarritoDeComprasController extends BaseControllerImpl<CarritoDeCompras, CarritoDeComprasServiceImpl>{

}
