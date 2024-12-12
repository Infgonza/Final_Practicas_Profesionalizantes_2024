package dto;

import java.util.List;

import lombok.Data;

@Data
public class CompraDTO {

	private double total;
	private List<ProductoDTO> productos;
}
