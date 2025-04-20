package com.example.sql_dbms_ui.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sql_dbms_ui.Services.EmployeeServices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sql_dbms_ui.Models.Employees;


@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    public final EmployeeServices employeeServices;

    EmployeeController(EmployeeServices employeeServices){
        this.employeeServices = employeeServices;
    }

    @GetMapping("{id}")
    public Employees getEmployeeInfo(@PathVariable("id") Long id) {
        return  employeeServices.getEmployeeById(id);
    }

    
    
}
