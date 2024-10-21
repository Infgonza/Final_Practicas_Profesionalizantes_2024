package config;

import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import repositories.RoleRepository;
import repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import entities.ERole;
import entities.RoleEntity;
import entities.Usuario;
import jakarta.transaction.Transactional;


@Component
public class DataInitializer implements CommandLineRunner{

	@Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
    	
    	iniciarRoles();
    	
    	crearAdminSiNoExiste();
    	
        }

    private void iniciarRoles() {
    	
    	try {
			
        	// Si en la base de datos no existe ningun rol, los creamos
        	if(roleRepository.count() == 0) {
        		
        		// Añadimos el rol de USUARIO
        		RoleEntity usuario = new RoleEntity();
        		usuario.setNombreRol(ERole.Usuario);
        		roleRepository.save(usuario);
        		
        		// Añadimos el rol de ADMINISTRADOR
        		RoleEntity administrador = new RoleEntity();
        		administrador.setNombreRol(ERole.Administrador);
        		roleRepository.save(administrador);
        		
        	} else {
        		System.out.println("Ya existen los roles: "+ roleRepository.findAll());
        	}
    		
		} catch (Exception e) {
			throw new RuntimeException("Error al crear roles", e);
		
		}
    
    }
    
    
    @Transactional
    private void crearAdminSiNoExiste() {
    	
    	try {
        	// Verificamos si en la base de datos existe un usuario llamado admin, si esta vacio cumplimos la condicion
        	if(usuarioRepository.findByNombreUsuario("admin").isEmpty()) {
        		
        		// Buscamos el rol de ADMINISTRADOR
        		RoleEntity rolAdmin = roleRepository.findByNombreRol(ERole.Administrador)
        				.orElseThrow(() -> new RuntimeException("ERROR: No se encuentra el rol ADMINISTRADOR"));
        		
        		// Creamos el usuario ADMINISTRADOR
        		if(rolAdmin != null ){
        			
        			Set<RoleEntity> roles = new HashSet<>();
        		    roles.add(rolAdmin);

        			Usuario admin = Usuario.builder()
        					.nombreUsuario("admin")
                            .email("admin@admin.com")
                            .contrasenia(passwordEncoder.encode("admin123"))
                            .rol("Administrador")
                            .roles(roles)
        					.build();
        			usuarioRepository.save(admin);	
        		}
        	} else {
        		System.out.println("Ya existe un usuario ADMIN");
        	}
		} catch (Exception e) {
			throw new RuntimeException("Error al crear el usuario ADMINISTRADOR", e);
		}
    	
    }
	

}
