package com.example.sql_dbms_ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for testing purposes
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**", "/Employees", "/save", "/update/**", "/delete/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll() // Allow public access
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
