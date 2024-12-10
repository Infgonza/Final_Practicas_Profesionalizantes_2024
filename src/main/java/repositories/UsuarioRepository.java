package repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import entities.Usuario;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    
    
    
}
