package services;

import org.springframework.stereotype.Service;

import entities.DetalleCompra;
import repositories.BaseRepository;

@Service
public class DetalleCompraServiceImpl extends BaseServiceImpl<DetalleCompra ,Long> implements DetalleCompraService{

	public DetalleCompraServiceImpl(BaseRepository<DetalleCompra, Long> baseRepository) {
		super(baseRepository);
	}

}
