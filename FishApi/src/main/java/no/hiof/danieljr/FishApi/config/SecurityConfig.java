package no.hiof.danieljr.FishApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .requestMatchers("/users/register", "/users/login").permitAll() // tillat register og login uten autentisering
                        .anyRequest().authenticated() // alt annet krever autentisering
                )
                .httpBasic(Customizer.withDefaults()); // basic auth for resten, kan endres senere

        return http.build();
    }
}

