package com.example.sql_dbms_ui.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.Services.AdminServices;


@RestController
@RequestMapping(value = "/api/admin")
public class AdminController{

    private final AdminServices adminServices;

    AdminController(AdminServices adminServices){
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
    @PatchMapping("/{empid}")
    public Employees updateEmployee(@PathVariable("empid") long EmpID, @RequestBody Employees employeeUpdates){
        return adminServices.updateEmployee(EmpID, employeeUpdates);
    }

    // Handles HTTP Delete request
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long EmpID) {
        adminServices.delete(EmpID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Employees> searchEmployees(
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String ssn,
        @RequestParam(required = false) Long empid
    ) {
        return adminServices.searchEmployees(firstName, lastName, ssn, empid);
    }

    @PatchMapping("/salary-adjustment")
    public ResponseEntity<?> adjustSalaries(
        @RequestParam double percentage,
        @RequestParam double minSalary,
        @RequestParam double maxSalary) {

        List<Employee> employees = employeeRepository.findBySalaryBetween(minSalary, maxSalary);

        for (Employee e : employees) {
            double updatedSalary = e.getSalary() * (1 + (percentage / 100));
            e.setSalary(updatedSalary);
        }

        employeeRepository.saveAll(employees);

        return ResponseEntity.ok("Salaries updated for " + employees.size() + " employees.");
    }
}
