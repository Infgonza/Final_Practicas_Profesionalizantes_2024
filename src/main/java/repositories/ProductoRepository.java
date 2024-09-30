package repositories;

import org.springframework.stereotype.Repository;

import entities.Producto;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long>{

}
