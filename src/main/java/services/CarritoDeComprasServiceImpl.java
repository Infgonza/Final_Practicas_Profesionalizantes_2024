package services;

import org.springframework.stereotype.Service;

import entities.CarritoDeCompras;
import repositories.BaseRepository;

@Service
public class CarritoDeComprasServiceImpl extends BaseServiceImpl<CarritoDeCompras ,Long> implements CarritoDeComprasService{

	public CarritoDeComprasServiceImpl(BaseRepository<CarritoDeCompras, Long> baseRepository) {
		super(baseRepository);

	}

}
