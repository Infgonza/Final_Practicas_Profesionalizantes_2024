package services;

import org.springframework.stereotype.Service;

import entities.Producto;
import repositories.BaseRepository;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto, Long> implements ProductoService{

	public ProductoServiceImpl(BaseRepository<Producto, Long> baseRepository) {
		super(baseRepository);
	}

}
