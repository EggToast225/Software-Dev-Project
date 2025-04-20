package com.example.sql_dbms_ui.Services;

import org.springframework.stereotype.Service;

import com.example.sql_dbms_ui.Models.Payroll;
import com.example.sql_dbms_ui.Models.Employees;

import com.example.sql_dbms_ui.repo.EmployeesRepo;
import com.example.sql_dbms_ui.repo.PayrollRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeServices {
    
    private final EmployeesRepo employeesRepo;
    private final PayrollRepo payrollRepo;

    public EmployeeServices(EmployeesRepo employeesRepo, PayrollRepo payrollRepo){
        this.employeesRepo = employeesRepo;
        this.payrollRepo = payrollRepo;
    }
    
    // b. A general employee able to see their data (SELECT) only, no changi
    // Search by employee to view their data. (Will require MySQL security...)
    // placeholder for later security implementation (should be checking on employee's authorization)

    public Employees getEmployeeById(long id){
        return employeesRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Employee not found with EmpId " + id));
    }

    // getPayroll
    public Payroll getPayrollById(long id){
        return payrollRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Employee's payroll not found with Id " + id));
    }

    public Employees getEmployeeByEmail(String email){
        return employeesRepo.findByEmail(email)
        .orElseThrow(()-> new EntityNotFoundException("Employee with email "+ email + " not found."));
    }
}
