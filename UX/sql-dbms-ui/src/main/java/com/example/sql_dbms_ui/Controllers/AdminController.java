package com.example.sql_dbms_ui.Controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public AdminController(AdminServices adminServices){
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
    public ResponseEntity<List<Employees>> findEmployeesBetweenSalary(
        @RequestParam(required = false) double minSalary,
        @RequestParam(required = false) double maxSalary) {

        if (minSalary <= 0 || maxSalary <= 0 || minSalary > maxSalary) {
            return ResponseEntity.badRequest().body(null);  // Return 400 if invalid parameters
        }

        try {
            List<Employees> result = adminServices.findEmployeesBetweenSalary(minSalary, maxSalary);
            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Return 204 if no employees found
            }
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            System.out.println("Error retrieving employees between salaries: "+ e.getMessage());  // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Return 500 if server error
        }
    }


    //  Update the salary ranges of employees between salary range.
    @PatchMapping("/update-salary")
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
    public ResponseEntity<?> getMonthlyNetPayByJobTitle(@RequestParam int year, @RequestParam int month) {
        try {
            Map<String, Double> results = adminServices.getTotalMonthlyEarningsByJobTitle(year, month);
            List<Map<String, Object>> formattedResults = results.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("jobTitle", entry.getKey());
                    result.put("earnings", entry.getValue());
                    // Create date with correct month (0-based in Calendar)
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month - 1, 1);
                    result.put("payDate", new java.sql.Date(cal.getTimeInMillis()));
                    return result;
                })
                .collect(Collectors.toList());
            
            // Log the raw and formatted results for debugging
            System.out.println("Raw Job Title Results: " + results);
            System.out.println("Formatted Job Title Results: " + formattedResults);
            return ResponseEntity.ok(formattedResults);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving job title payroll: " + e.getMessage());
        }
    }

    @GetMapping("/job-titles/yearly-net-pay")
    public ResponseEntity<?> getYearlyNetPayByJobTitle(@RequestParam int year) {
        try {
            Map<String, Double> results = adminServices.getTotalYearlyEarningsByJobTitle(year);
            List<Map<String, Object>> formattedResults = results.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("jobTitle", entry.getKey());
                    result.put("earnings", entry.getValue());
                    // Create date with January 1st of the year
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, 0, 1);
                    result.put("payDate", new java.sql.Date(cal.getTimeInMillis()));
                    return result;
                })
                .collect(Collectors.toList());
            return ResponseEntity.ok(formattedResults);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving yearly job title payroll: " + e.getMessage());
        }
    }

    @GetMapping("/division/monthly-net-pay")
    public ResponseEntity<?> getMonthlyNetPayByDivision(@RequestParam int year, @RequestParam int month) {
        try {
            Map<String, Double> results = adminServices.getTotalMonthlyEarningsByDivision(year, month);
            List<Map<String, Object>> formattedResults = results.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("division", entry.getKey());
                    result.put("earnings", entry.getValue());
                    // Create date with correct month (0-based in Calendar)
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month - 1, 1);
                    result.put("payDate", new java.sql.Date(cal.getTimeInMillis()));
                    return result;
                })
                .collect(Collectors.toList());
            return ResponseEntity.ok(formattedResults);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving division payroll: " + e.getMessage());
        }
    }

    @GetMapping("/division/yearly-net-pay")
    public ResponseEntity<?> getYearlyNetPayByDivision(@RequestParam int year) {
        try {
            Map<String, Double> results = adminServices.getTotalYearlyEarningsByDivision(year);
            List<Map<String, Object>> formattedResults = results.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("division", entry.getKey());
                    result.put("earnings", entry.getValue());
                    // Create date with January 1st of the year
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, 0, 1);
                    result.put("payDate", new java.sql.Date(cal.getTimeInMillis()));
                    return result;
                })
                .collect(Collectors.toList());
            return ResponseEntity.ok(formattedResults);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving yearly division payroll: " + e.getMessage());
        }
    }

    @GetMapping("/division/monthly-net-pay/{empid}")
    public ResponseEntity<?> findByEmployeeEmpidOrderByPayDateDesc(@PathVariable Long empid) {
        try {
            List<Payroll> payrolls = adminServices.findByEmployeeEmpidOrderByPayDateDesc(empid);
            return ResponseEntity.ok(payrolls);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving employee payroll: " + e.getMessage());
        }
    }

    @GetMapping("/payroll/employee/{empid}/asc")
    public ResponseEntity<?> findByEmployeeEmpidOrderByPayDateAsc(@PathVariable Long empid) {
        try {
            List<Payroll> payrolls = adminServices.findByEmployeeEmpidOrderByPayDateAsc(empid);
            return ResponseEntity.ok(payrolls);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving employee payroll: " + e.getMessage());
        }
    }

    @GetMapping("/payroll/all")
    public ResponseEntity<?> findAllByOrderByEmployeeEmpidAscPayDateAsc() {
        try {
            List<Payroll> payrolls = adminServices.findAllByOrderByEmployeeEmpidAscPayDateAsc();
            return ResponseEntity.ok(payrolls);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving all payroll records: " + e.getMessage());
        }
    }

    @GetMapping("/test-payroll-query")
    public ResponseEntity<?> testPayrollQuery() {
        try {
            Calendar cal = Calendar.getInstance();
            // Set to March 1, 2024
            cal.set(2024, 2, 1, 0, 0, 0); // Month is 0-based, so 2 = March
            Date startDate = cal.getTime();
            
            // Set to March 31, 2024
            cal.set(2024, 2, 31, 23, 59, 59);
            Date endDate = cal.getTime();
            
            List<Map<String, Object>> results = adminServices.findTotalMonthlyEarningsByJobTitle(startDate, endDate);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error testing payroll query: " + e.getMessage());
        }
    }

    @GetMapping("/divisions")
    public ResponseEntity<?> getAllDivisions() {
        try {
            List<String> divisions = adminServices.getAllDivisions();
            return ResponseEntity.ok(divisions);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving divisions: " + e.getMessage());
        }
    }

}
