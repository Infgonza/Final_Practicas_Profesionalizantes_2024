package services;

import org.springframework.stereotype.Service;

import entities.Disco;
import repositories.BaseRepository;

@Service
public class DiscoServiceImpl extends BaseServiceImpl<Disco, Long> implements DiscoService{

	public DiscoServiceImpl(BaseRepository<Disco, Long> baseRepository) {
		super(baseRepository);
	}

}
