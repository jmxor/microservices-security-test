package com.jmxor.demo_app_server;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class DemoAppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {
        http
            // CORS (only needed if frontend JS calls this server)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // CSRF enabled by default (keep it for login flow)
            .csrf(
                csrf -> csrf.ignoringRequestMatchers("/api/**") // optional
            )
            // Session REQUIRED for OAuth2 login
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            // Authorization rules
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers("/", "/error")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            // OAuth2 login flow
            .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/", true))
            // Logout handling
            .logout(logout ->
                logout
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("https://app.local.test"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
