package com.example.sql_dbms_ui.Controllers;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.Services.EmployeeServices;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController{

    private final EmployeeServices employeeServices;

    // hardcoded authentication for testing
    private final Map<String, String> adminCreds = Map.of(
        "admin1", "adminpass"
    );

    private final Map<String, String> employeeCreds = Map.of(
        "emp1", "password1",
        "emp2", "password2"
    );

    public AuthController(EmployeeServices employeeServices) {
        this.employeeServices = employeeServices;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> login){
        String email = login.get("email");
        String password = login.get("password");

        // Admin Login Check
        if (adminCreds.containsKey(email) && adminCreds.get(email).equals(password)){
            return ResponseEntity.ok(Map.of(
                "role", "admin"
            ));
        }

        // Employee Login Check
        if (employeeCreds.containsKey(email) && employeeCreds.get(email).equals(password)){
            Employees employee = employeeServices.getEmployeeByEmail(email);
            if (employee == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
            }
            return ResponseEntity.ok(Map.of(
                "role", "employee",
                "empid", employee.getEmpid()
            ));
        }
        
        // otherwise return error
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }
}