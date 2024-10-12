package controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoViewController {

   
	/*@GetMapping("/subirproductos")
    public String mostrarFormularioSubirProductos(@AuthenticationPrincipal Object principal) {
        if (principal == null) {
            return "redirect:/login"; // Redirige a login si no hay sesión
        }
        return "subirproductos"; 
    }*/

    @GetMapping("/productos")
    public String productos() {
        return "productos"; // Renderiza la página Thymeleaf
    }
}
