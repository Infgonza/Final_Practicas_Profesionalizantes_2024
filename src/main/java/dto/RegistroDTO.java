package dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class RegistroDTO {
    private String nombreUsuario;
    private String email;
    private String contrasenia;
    private String telefono;
    private String rol = "Usuario";
    private Set<String> roles;
}
