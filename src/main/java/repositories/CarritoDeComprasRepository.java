package repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import entities.CarritoDeCompras;

@Repository
public interface CarritoDeComprasRepository extends BaseRepository<CarritoDeCompras, Long> {
	Optional<CarritoDeCompras> findByUsuarioIdUsuario(Long idUsuario);
	
	
	

}
