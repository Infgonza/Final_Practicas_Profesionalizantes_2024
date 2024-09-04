package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.*;


public class PersistenceApp {
	public static void main(String[] args) {
		
		// COMPLETAR CON LA BASE DE DATOS
		 
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceAppPU");
		 
		 EntityManager em = emf.createEntityManager();
		  
		 
		
		// REALIZAR EL TRY & CATCH 
		try {

			em.getTransaction().begin();

			// Creamos un cliente con un domicilio y un usuario para poder confirmar de que funciona la base de datos
			Cliente cliente = new Cliente("Daniel", "Perez", 123456789);
			Domicilio domicilio = new Domicilio("San Martin", 123, 5515, "Mendoza");
			Usuario usuario = new Usuario("DanelP", "PerezDan@gmail.com", "123456", "Usuario");

			// Asignamos a cliente el domicilio y al usuario (para probar relaciones)
			cliente.setDomicilio(domicilio);
			cliente.setUsuario(usuario);

			em.persist(cliente);

			em.flush();

			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
		}

		em.close();
		emf.close();
	}

}
