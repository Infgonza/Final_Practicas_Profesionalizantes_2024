package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        
        return httpSecurity
        		.authorizeHttpRequests()
        			.requestMatchers("/**.html", "/**.css/", "http://localhost:8080/api/v1/auth/registro").permitAll()
        			.requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**", "/imagenes/**").permitAll()
        			.anyRequest().authenticated()
        		.and()
        		.formLogin().permitAll()
        		.and()
        		.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configuración del codificador de contraseñas
    }
}