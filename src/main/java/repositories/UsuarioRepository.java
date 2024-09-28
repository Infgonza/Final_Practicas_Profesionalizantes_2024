package repositories;

import org.springframework.stereotype.Repository;

import entities.Usuario;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

}
