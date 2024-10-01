package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Compra;
import repositories.BaseRepository;
import repositories.CompraRepository;

@Service
public class CompraServiceImpl extends BaseServiceImpl<Compra, Long> implements CompraService{

	@Autowired
	private CompraRepository compraRepository;
	
	public CompraServiceImpl(BaseRepository<Compra, Long> baseRepository) {
		super(baseRepository);
	}

}
