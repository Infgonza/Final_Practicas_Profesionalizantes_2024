package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.*;

public class PersistenceApp {
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceAppPU");
		EntityManager em = emf.createEntityManager();
		

		
		try {
			
			em.getTransaction().begin();
			
			Producto producto1 = Producto.builder()
					.nombre("Toro Rojo")
					.descripción("Disco Compacto")
					.precio(7800.00)
					.stock(10)
					.build();
			

			
			Cliente cliente1 = Cliente.builder()
					.nombre("Juan")
					.apellido("Perez")
					.dni(45190777)
					.domicilio(Domicilio.builder()
							.calle("Godoy Cruz")
							.numero(1655)
							.codPostal(5515)
							.provincia("Mendoza")
							.build())
					.usuario(Usuario.builder()
							.nombreUsuario("JuanPerez")
							.email("PeJuan@mail.com")
							.contrasenia("12345")
							.rol("Cliente")
							.build())
					.carrito(CarritoDeCompras.builder()
							.
							build())
					.build();
			
			em.persist(cliente1);
			em.persist(producto1);
			em.flush();
			em.getTransaction().commit();
			
		} catch (Exception e) {
			
			em.getTransaction().rollback();
		}
		
		em.close();
		emf.close();
		
	}

}
