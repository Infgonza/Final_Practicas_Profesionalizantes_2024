package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceApp {
	public static void main(String[] args) {
		
		//COMPLETAR CON LA BASE DE DATOS
		 
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("disqueria");
		 
		 EntityManager em = emf.createEntityManager();
		  
		 
		
		// REALIZAR EL TRY & CATCH 
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
