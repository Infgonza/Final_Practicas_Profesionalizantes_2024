package services;

import org.springframework.stereotype.Service;

import entities.Factura;
import repositories.BaseRepository;

@Service
public class FacturaServiceImpl extends BaseServiceImpl<Factura, Long> implements FacturaService {

	public FacturaServiceImpl(BaseRepository<Factura, Long> baseRepository) {
		super(baseRepository);
	}

}
