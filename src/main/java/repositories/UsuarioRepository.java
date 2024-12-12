package repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import entities.Usuario;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    @Query("SELECT u FROM Usuario u ORDER BY u.id DESC LIMIT 1")
    Optional<Usuario>findTopByOrderByIdDesc();

    
    
}
