package controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entities.DetalleCompra;
import services.DetalleCompraServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/detalle_compras")
public class DetalleCompraController extends BaseControllerImpl<DetalleCompra, DetalleCompraServiceImpl>{



}
