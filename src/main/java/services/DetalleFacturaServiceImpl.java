package services;

import org.springframework.stereotype.Service;

import entities.DetalleFactura;
import repositories.BaseRepository;

@Service
public class DetalleFacturaServiceImpl extends BaseServiceImpl<DetalleFactura, Long> implements DetalleFacturaService{

	public DetalleFacturaServiceImpl(BaseRepository<DetalleFactura, Long> baseRepository) {
		super(baseRepository);
	}

}
