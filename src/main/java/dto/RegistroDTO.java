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
    private Set<String> roles;
}
