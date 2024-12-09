package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import services.JwtService;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {
	
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
   

    


    @Bean
    JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtService, userDetailsService);
    }



    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/subirproductos.html", "/usuarios.html", "/contacto/enviar").permitAll() // Permitir acceso a la página
                .requestMatchers("/api/v1/productos/verificarPermisoSubir", "/api/v1/usuarios/listar", "/api/v1/productos/listar")
                .hasAnyAuthority("Administrador", "Empleado")              
                .requestMatchers(HttpMethod.GET, "/**.html", "/api/v1/productos", "/productos" ).permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/usuarios/cambiarRol/**").hasAuthority("Administrador")
                .requestMatchers("/api/carrito/**", "/api/v1/usuarios/**").authenticated()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**", "/imagenes/**", "/favicon.ico").permitAll() 
                
                .anyRequest().authenticated())
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}