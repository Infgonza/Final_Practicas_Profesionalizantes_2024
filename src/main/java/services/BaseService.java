package services;

import java.io.Serializable;
import java.util.List;

// La hacemos genérica para que pueda recibir cualquier entidad
// E --> Seria cualquer entidad
public interface BaseService<E, ID extends Serializable>{
	
	// Nos trae una lista de la base de datos de toda una Entidad
	public List<E> findAll() throws Exception;
	
	// Nos trae por id una entidad de la base de datos
	public E findById(ID id) throws Exception;
	
	// Crea una nueva entidad en la base de datos
	public E save(E entity) throws Exception;
	
	// Actualizamos algun registro de la base de datos ya creado
	public E update(ID id, E entity) throws Exception;
	
	// Eliminamos un registro de la base de datos
	public boolean delete(ID id) throws Exception;

}
