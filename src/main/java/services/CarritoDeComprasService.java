package services;

import entities.CarritoDeCompras;
import entities.Producto;
import entities.Usuario;

public interface CarritoDeComprasService extends BaseService<CarritoDeCompras, Long>{
	  CarritoDeCompras getOrCreateCarritoForUser(Usuario usuario);
	    void addItemToCarrito(Usuario usuario, Producto producto, int cantidad);
	    void removeItemFromCarrito(Usuario usuario, Producto producto);
	    void updateItemQuantity(Usuario usuario, Producto producto, int newQuantity);

}
