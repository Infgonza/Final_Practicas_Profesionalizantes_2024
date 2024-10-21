package repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import entities.ERole;
import entities.RoleEntity;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity, Long> {
	Optional<RoleEntity> findByNombreRol(ERole nombreRol);
    boolean existsByNombreRol(ERole nombreRol);
}
