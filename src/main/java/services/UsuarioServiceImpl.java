package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Usuario;
import repositories.BaseRepository;
import repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(BaseRepository<Usuario, Long> baseRepository) {
		super(baseRepository);
	}

	
	


}
