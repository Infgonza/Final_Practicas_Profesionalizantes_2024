package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.CarritoDeCompras;
import entities.Producto;
import entities.Usuario;
import repositories.BaseRepository;

@Service
public class CarritoDeComprasServiceImpl extends BaseServiceImpl<CarritoDeCompras ,Long> implements CarritoDeComprasService{
	 @Autowired
	    private UsuarioService usuarioService;

	    @Autowired
	    private ProductoService productoService;

	public CarritoDeComprasServiceImpl(BaseRepository<CarritoDeCompras, Long> baseRepository) {
		super(baseRepository);

	}

	@Override
	public CarritoDeCompras getOrCreateCarritoForUser(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addItemToCarrito(Usuario usuario, Producto producto, int cantidad) {
		// TODO Auto-generated method stub
		
	}

	public void updateItemQuantity(Usuario usuario, Long productoId, int cantidad) {
		// TODO Auto-generated method stub
		
	}

	public void removeItemFromCarrito(Usuario usuario, Long productoId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeItemFromCarrito(Usuario usuario, Producto producto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateItemQuantity(Usuario usuario, Producto producto, int newQuantity) {
		// TODO Auto-generated method stub
		
	}

	


}
