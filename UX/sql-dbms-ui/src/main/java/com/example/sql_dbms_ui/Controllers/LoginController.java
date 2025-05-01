package com.example.sql_dbms_ui.Controllers;



import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Controller that handles login requests

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    
    public LoginController(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verifySession() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest){
        try{
            Authentication authenticationRequest = 
                UsernamePasswordAuthenticationToken.unauthenticated(
                    loginRequest.username(), loginRequest.password());
            
            // Authentication Manager handles authentication process in backend and sends response to frontend
            Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

            String role = authenticationResponse.getAuthorities().stream()
                .findFirst()
                .map(granted -> granted.getAuthority().replace("ROLE_", ""))
                .orElse("UNKNOWN");

        return ResponseEntity.ok(Map.of("role", role));

        } catch (BadCredentialsException e){
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password"));
        }
        
    }
    public record LoginRequest(String username, String password){
    }
}