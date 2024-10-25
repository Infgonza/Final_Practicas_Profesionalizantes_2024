package controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoViewController {

    @GetMapping("/productos")
    public String productos() {
        return "productos"; // Renderiza la página Thymeleaf
    }
}
