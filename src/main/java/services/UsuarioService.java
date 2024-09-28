package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Usuario;
import jakarta.transaction.Transactional;
import repositories.UsuarioRepository;

@Service
public class UsuarioService implements BaseService<Usuario>{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Transactional
	public List<Usuario> findAll() throws Exception {
		try {
			List<Usuario> entities = usuarioRepository.findAll();
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public Usuario findById(Long id) throws Exception {
		try {
			Optional<Usuario> entityOptional = usuarioRepository.findById(id);
			return entityOptional.get();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public Usuario save(Usuario entity) throws Exception {
		try {
			entity = usuarioRepository.save(entity);
			return entity;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public Usuario update(Long id, Usuario entity) throws Exception {
		try {
			Optional<Usuario> entityOptional = usuarioRepository.findById(id);
			Usuario usuario = entityOptional.get();
			usuario = usuarioRepository.save(entity);
			return usuario;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public boolean delete(Long id) throws Exception {
		try {
			if (usuarioRepository.existsById(id)) {
				usuarioRepository.deleteById(id);
				return true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
