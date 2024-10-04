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
            .csrf().disable()  // Deshabilitar CSRF para pruebas de API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**.html", "/api/v1/auth/**", "/productos").permitAll()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**", "/imagenes/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin().disable()  // Deshabilitar el formulario de login por defecto
            .httpBasic().disable()  // Deshabilitar autenticación básica
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}