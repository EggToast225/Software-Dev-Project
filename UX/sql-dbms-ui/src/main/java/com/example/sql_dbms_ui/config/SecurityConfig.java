package com.example.sql_dbms_ui.config;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()
                /*
                .requestMatchers("/api/admin/**").hasRole("ADMIN")       // Admin role are allowed to send request from api/admin/** 
                .requestMatchers("/api/employee/**").hasRole("EMPLOYEE") // Employee roles are allowed to send requests from api/employee/** 
                .requestMatchers("/api/auth/**").permitAll()                 // Login endpoint
                
                .anyRequest().authenticated()
                */
                /*
                .formLogin(form -> form
                .loginPage("/api/auth/login")
                .permitAll()
                */
            )
            .logout(logout ->
                logout.permitAll()
            );

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // frontend origin
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // Important if using cookies or authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public UserDetailsService UserDetailsService(){
        // create a  InMemoryManager for hardcoded credentials
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        
        // Create a user with Admin privileges
        manager.createUser(
            User.withUsername("admin")
            .password("{noop}admin123") // {noop} means no password encoding, so it's just the string
            .roles("ADMIN")
            .build());
        // Create a user with Employee privileges
        manager.createUser(
            User.withUsername("employee")
            .password("{noop}employee123") // {noop} means no password encoding, so it's just the string
            .roles("EMPLOYEE")
            .build());
            
        return manager;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);

        return new ProviderManager(authProvider);

    }
}
