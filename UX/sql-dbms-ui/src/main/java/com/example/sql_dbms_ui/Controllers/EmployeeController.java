package com.example.sql_dbms_ui.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.Services.AdminServices;


@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeController{

    private final AdminServices adminServices;

    EmployeeController(AdminServices adminServices){
        this.adminServices = adminServices;
    }


    //@PostMapping used because we want to save to database; saves new employee
    @PostMapping
    public ResponseEntity<Employees> saveEmployee(@RequestBody Employees newEmployee) {
        adminServices.createUser(newEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    //Get all employees
    @GetMapping
    public List<Employees> getAllEmployees(){
        return adminServices.getAllEmployees();
    }

    // Updates the User by their primary key/id, handles HTTP Patch request
    // Right now, it's just first and last name being updated.
    @PatchMapping
    public String updateEmployee(@PathVariable("empid") long EmpID, @RequestBody Employees employeeUpdates){
        Employees employee = adminServices.getEmployeeById(EmpID);
        adminServices.updateEmployee(employee);

        return "updated";
    }

    // Handles HTTP Delete request
    @DeleteMapping 
    public String deleteEmployee(@PathVariable("id") long EmpID, @RequestBody Employees employee){
        Employees deleteUser = adminServices.getEmployeeById(EmpID);
        adminServices.delete(deleteUser);
        return "deleted user with id: " + EmpID;
    }
}
