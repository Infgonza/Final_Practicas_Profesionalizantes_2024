package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DomicilioDto {
	@NotBlank(message = "La calle es obligatoria.")
    private String calle;

    @NotNull(message = "El número es obligatorio.")
    private Integer numero;

    @NotNull(message = "El código postal es obligatorio.")
    private Integer codPostal;

    @NotBlank(message = "La provincia es obligatoria.")
    private String provincia;

    
}
