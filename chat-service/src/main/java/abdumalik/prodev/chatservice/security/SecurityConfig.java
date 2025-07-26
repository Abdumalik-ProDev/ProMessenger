package abdumalik.prodev.chatservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Allow WebSocket connections and Swagger UI
                        .requestMatchers("/ws/chat/**", "/swagger-ui/**", "/api-docs/**").permitAll()
                        // Secure all other API endpoints
                        .anyRequest().authenticated()
                );
        // In a real app, you would add a JwtAuthenticationFilter here as well for defense-in-depth
        return http.build();
    }

}