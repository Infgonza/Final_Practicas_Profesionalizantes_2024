package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.EmailService;

 
@RestController
@RequestMapping(path = "/contacto")
public class ContactoController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public String enviarFormulario(
        @RequestParam String nombre,
        @RequestParam String email,
        @RequestParam String mensaje,
        RedirectAttributes redirectAttributes
    ) {
        try {
            // Lógica para enviar el correo
            String cuerpoMensaje = String.format("Nombre: %s\nCorreo: %s\nMensaje:\n%s", nombre, email, mensaje);
            emailService.enviarCorreo("grooverecords28@gmail.com", "Nuevo mensaje", cuerpoMensaje);

            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("mensaje", "¡El correo se envió correctamente!");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            // Mensaje de error
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un problema al enviar el correo.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        // Redirigir a la página del formulario
        return "redirect:/contacto.html"; 
    }
}
