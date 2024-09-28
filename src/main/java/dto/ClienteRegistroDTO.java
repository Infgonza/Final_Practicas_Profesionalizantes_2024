package dto;

import lombok.Data;

@Data
public class ClienteRegistroDTO {

	// Datos de Cleinte
    private String nombre;
    private String apellido;
    private int dni;
    
    // Datos de Domicilio
    private String calle;
    private int numero;
    private int codPostal;
    private String provincia;
    
    // Datos de Usuario
    private String nombreUsuario;
    private String email;
    private String contrasenia;
    private String rol;
	
}
