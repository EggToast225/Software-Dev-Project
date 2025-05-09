package com.example.sql_dbms_ui.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.Models.Payroll;
import com.example.sql_dbms_ui.repo.EmployeesRepo;
import com.example.sql_dbms_ui.repo.PayrollRepo;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeesRepo employeesRepository;
    private final PayrollRepo payrollRepository;

    public EmployeeController(EmployeesRepo employeesRepository, PayrollRepo payrollRepository) {
        this.employeesRepository = employeesRepository;
        this.payrollRepository = payrollRepository;
    }

    @GetMapping("/details")
    public ResponseEntity<?> getEmployeeDetails(@RequestParam String email) {
        Optional<Employees> employeeOpt = employeesRepository.findByEmail(email);
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Employees employee = employeeOpt.get();
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/payroll")
    public ResponseEntity<?> getEmployeePayroll(@RequestParam String email) {
        Optional<Employees> employeeOpt = employeesRepository.findByEmail(email);
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Payroll> payrollRecords = payrollRepository.findByEmployeeEmpid(employeeOpt.get().getEmpid());
        return ResponseEntity.ok(payrollRecords);
    }

    @GetMapping("/job-title")
    public ResponseEntity<?> getEmployeeJobTitle(@RequestParam String email) {
        Optional<Employees> employeeOpt = employeesRepository.findByEmail(email);
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Get the employee's current job title using a native query
        String jobTitle = employeesRepository.findCurrentJobTitleByEmployeeId(employeeOpt.get().getEmpid());
        if (jobTitle == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of("title", jobTitle));
    }
}
