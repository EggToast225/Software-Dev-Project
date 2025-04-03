package com.example.sql_dbms_ui.Services;

import java.util.Date;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Optional;

import org.springframework.stereotype.Service;



import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.repo.EmployeesRepo;

import jakarta.persistence.EntityNotFoundException;

// Business Logic for Admin side

@Service
public class AdminServices {
    
    private final EmployeesRepo employeesRepo;

    public AdminServices(EmployeesRepo employeesRepo){
        this.employeesRepo = employeesRepo;
    }

    // Create new user
    public void createUser(Employees newEmployee){
        Employees employee = new Employees(
            newEmployee.getFirstName(),
            newEmployee.getLastName(),
            newEmployee.getEmail(),
            newEmployee.getHireDate(),
            newEmployee.getSalary(),
            newEmployee.getSSN(),
            newEmployee.getAddress(),
            newEmployee.getGender(),
            newEmployee.getIdentifiedRace(),
            newEmployee.getDob(),
            newEmployee.getPhone()
            );
        employeesRepo.save(employee);
    };
    // Get all Employees
    public List<Employees> getAllEmployees(){
        return employeesRepo.findAll();
    }

    
    public void updateEmployee(Employees employee){
        /*
            takes in a json request, should only update the fields on that JSON

            Example
            Database for Employee contains
        {
            "id": 1,
            "firstName": "John",
            "lastName": "Doe"
            }
        Input is {"firstName": "joe"}
        expected output
        {
            "id": 1,
            "firstName": "joe",
            "lastName": "Doe"
        }
        Should update the any field with a new value
        */
        employeesRepo.save(employee);
    }

    //Search for an employee using name, DOB, SSN, empid to show their information for editing
    //(Admin only)

    public Employees getEmployeeById(long id){
        return employeesRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found with EmpId" + id));
    }

    public Employees getEmployeeBySSN(String ssn){
        return employeesRepo.findBySSN(ssn).orElseThrow(() -> new EntityNotFoundException("Employee not found with SSN:" + ssn));
    }
    
    public Employees getEmployeeByDOB(Date dob){
        return employeesRepo.findByDOB(dob).orElseThrow(()-> new EntityNotFoundException("Employees not found with Date of Birth:" + dob));
    }

    public Employees getEmployeesByFirstName(String firstName){
        return employeesRepo.findByFirstName(firstName).orElseThrow(()-> new EntityNotFoundException("Employee not found with first name" + firstName));
    }

    public Employees getEmployeesByLastName(String lastName){
        return employeesRepo.findByLastName(lastName).orElseThrow(()-> new EntityNotFoundException("Employee not found with last name" + lastName));
    }
    

    public void updateEmployeeSalary(Long id ,double newSalary){
        Employees employee = employeesRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found with EmpId" + id));
        employee.setSalary(newSalary);
        employeesRepo.save(employee);
    }
}
