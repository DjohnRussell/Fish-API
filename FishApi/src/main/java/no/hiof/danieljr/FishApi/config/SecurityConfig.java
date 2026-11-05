package no.hiof.danieljr.FishApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.DispatcherType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // midlertidig deaktivert for testing
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers("/users/register", "/users/login").permitAll() // tillat register og login uten autentisering
                        .requestMatchers("/species/**").permitAll() // public access to fish species reference data
                        .anyRequest().authenticated() // alt annet krever autentisering
                )
                .httpBasic(Customizer.withDefaults()); // basic auth for resten, kan endres senere

        return http.build();
    }
}

