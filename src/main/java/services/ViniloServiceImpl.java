package services;

import org.springframework.stereotype.Service;

import entities.Vinilo;
import repositories.BaseRepository;

@Service
public class ViniloServiceImpl extends BaseServiceImpl<Vinilo, Long> implements ViniloService {

	public ViniloServiceImpl(BaseRepository<Vinilo, Long> baseRepository) {
		super(baseRepository);

	}

}
