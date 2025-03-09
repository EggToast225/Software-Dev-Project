package com.example.sql_dbms_ui.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.repo.EmployeesRepo;


@RestController
@RequestMapping(value = "/Employees")
public class EmployeeController{

    @Autowired // Handles dependency injection
    private EmployeesRepo employeeRepo;


    //@PostMapping used because we want to save to database; saves new employee
    @PostMapping(value = "/save") 
    public ResponseEntity<String> saveEmployees(@RequestBody List<Employees> employees) {
        employeeRepo.saveAll(employees);
        return ResponseEntity.ok("saved");
    }

    //Get all employees
    @GetMapping(value = "/all")
    public List<Employees> getEmployees(){
        return employeeRepo.findAll();
    }

    //Updates the User by their primary key/id, handles HTTP Put request
    @PutMapping(value ="/update/{empid}") 
    public String updateEmployee(@PathVariable("empid") long EmpID, @RequestBody Employees employee){
        Employees updatedEmployee = employeeRepo.findById(EmpID).get();
        updatedEmployee.setFirstName(employee.getFirstName());
        updatedEmployee.setLastName(employee.getLastName());
        updatedEmployee.setEmail(employee.getEmail());

        employeeRepo.save(updatedEmployee);
        return "updated";
    }

    @DeleteMapping(value = "/delete/{id}") // Handles HTTP Delete request
    public String deleteEmployee(@PathVariable("id") long EmpID, @RequestBody Employees employee){
        Employees deleteUser = employeeRepo.findById(EmpID).get();
        employeeRepo.delete(deleteUser);
        return "deleted user with id: " + EmpID;
    }
}
