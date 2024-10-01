package controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entities.Vinilo;
import services.ViniloServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/vinilos")
public class ViniloController extends BaseControllerImpl<Vinilo, ViniloServiceImpl>{

}
