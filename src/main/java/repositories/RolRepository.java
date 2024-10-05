package repositories;

import org.springframework.stereotype.Repository;

import entities.RoleEntity;

@Repository
public interface RolRepository extends BaseRepository<RoleEntity, Long> {

}
