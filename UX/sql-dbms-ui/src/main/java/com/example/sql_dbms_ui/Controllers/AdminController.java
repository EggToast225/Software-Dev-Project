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
import com.example.sql_dbms_ui.Models.Payroll;
import com.example.sql_dbms_ui.Services.AdminServices;
import com.example.sql_dbms_ui.Services.DTO.EmployeeDTO;

import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping(value = "/api/admin")
public class AdminController{

    // Establish a service layer from controller to AdminServices

    private final AdminServices adminServices;

    private AdminController(AdminServices adminServices){
        this.adminServices = adminServices;
    }

    // Create and save a new employee
    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDTO newEmployee) {
        adminServices.createUser(newEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    // Return a list of all employees
    @GetMapping
    public List<Employees> getAllEmployees(){
        return adminServices.getAllEmployees();
    }

    // Updates user information
    @PatchMapping("/{empid}")
    public ResponseEntity<Employees> updateEmployee(@PathVariable("empid") long EmpID, @RequestBody Employees employeeUpdates){
        try{
            Employees updatedEmployee = adminServices.updateEmployee(EmpID, employeeUpdates); // Returns an employee
            return ResponseEntity.ok(updatedEmployee);
        } catch (EntityNotFoundException e) { // No Employee found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception e){ // Error from server
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Handles HTTP Delete request
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long EmpID) {
        adminServices.delete(EmpID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employees>> searchEmployees(
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String ssn,
        @RequestParam(required = false) Long empid)
        {
            try {
                List<Employees> results = adminServices.searchEmployees(firstName, lastName, ssn, empid);
                return ResponseEntity.ok(results); // return a list of employees, including empty ones
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // return error message
            }
    }

    // Provide a list of employees between salary range.
    @GetMapping("/search-salary")
    public List<Employees> findEmployeesBetweenSalary(double minSalary, double maxSalary){
        return adminServices.findEmployeesBetweenSalary(minSalary, maxSalary);
    }

    //  Update the salary ranges of employees between salary range.
    @PatchMapping("/salary-adjustment")
    public ResponseEntity<?> updateEmployeeSalary(
        @RequestParam double percentage,
        @RequestParam double minSalary,
        @RequestParam double maxSalary
        ){
            adminServices.updateEmployeesSalary(percentage, minSalary, maxSalary);
            return ResponseEntity.ok("Employees have been updated");
        }

    @GetMapping("/get_payroll")
    public ResponseEntity<?> getPayrollById(Long id){
        return ResponseEntity.ok(adminServices.getPayrollById(id));
    }

    @GetMapping("/job-titles/monthly-net-pay")
    public ResponseEntity<?> getMonthlyNetPayByJobTitle(@RequestParam int year,@RequestParam int month) {
        return ResponseEntity.ok(adminServices.getTotalMonthlyEarningsByJobTitle(year, month));
    }

    @GetMapping("/division/monthly-net-pay")
    public ResponseEntity<?> getMonthlyNetPayByDivision(@RequestParam int year,@RequestParam int month) {
        return ResponseEntity.ok(adminServices.getTotalMonthlyEarningsByDivision(year, month));
    }

    @GetMapping("/division/monthly-net-pay/{empid}")
    public ResponseEntity<?> findByEmployeeEmpidOrderByPayDateDesc(@PathVariable Long empid){
        return ResponseEntity.ok(adminServices.findByEmployeeEmpidOrderByPayDateDesc(empid));
    }

    public ResponseEntity<?> findByEmployeeEmpidOrderByPayDateAsc(Long empid){
        return ResponseEntity.ok(adminServices.findByEmployeeEmpidOrderByPayDateAsc(empid));
    }

    public ResponseEntity<?> findAllByOrderByEmployeeEmpidAscPayDateAsc(){
        return ResponseEntity.ok(adminServices.findAllByOrderByEmployeeEmpidAscPayDateAsc());
    }
}
