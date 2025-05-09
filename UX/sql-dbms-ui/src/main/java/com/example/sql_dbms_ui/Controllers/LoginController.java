package com.example.sql_dbms_ui.Controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.repo.EmployeesRepo;

// Controller that handles login requests

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final EmployeesRepo employeesRepository;

    public LoginController(AuthenticationManager authenticationManager, EmployeesRepo employeesRepository) {
        this.authenticationManager = authenticationManager;
        this.employeesRepository = employeesRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.username();
        String password = loginRequest.password();

        // First try Spring Security authentication (for admin)
        try {
            Authentication authenticationRequest = 
                UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            
            Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

            String role = authenticationResponse.getAuthorities().stream()
                .findFirst()
                .map(granted -> granted.getAuthority().replace("ROLE_", ""))
                .orElse("UNKNOWN");

            return ResponseEntity.ok(Map.of("role", role));
        } catch (BadCredentialsException e) {
            // If Spring Security authentication fails, try employee authentication
            Optional<Employees> employeeOpt = employeesRepository.findByEmail(username);
            if (employeeOpt.isPresent()) {
                Employees employee = employeeOpt.get();
                // Get last 4 digits of SSN
                String lastFourSSN = employee.getSsn().substring(employee.getSsn().length() - 4);
                if (lastFourSSN.equals(password)) {
                    return ResponseEntity.ok(Map.of(
                        "role", "EMPLOYEE",
                        "email", employee.getEmail()
                    ));
                }
            }
        }

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Invalid username or password"));
    }

    public record LoginRequest(String username, String password) {}
}