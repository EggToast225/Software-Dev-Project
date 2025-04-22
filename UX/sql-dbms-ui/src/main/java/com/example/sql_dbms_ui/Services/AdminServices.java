package com.example.sql_dbms_ui.Services;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.repo.EmployeesRepo;

import jakarta.persistence.EntityNotFoundException;

// Business Logic for Admin side, part of service layer

@Service
public class AdminServices {

    private final EmployeesRepo employeesRepo;

    public AdminServices(EmployeesRepo employeesRepo){
        this.employeesRepo = employeesRepo;
    }

    // Create new user then stores it in database
    public void createUser(Employees newEmployee){
        Employees employee = new Employees(
            newEmployee.getFirstName(),
            newEmployee.getLastName(),
            newEmployee.getEmail(),
            newEmployee.getHireDate(),
            newEmployee.getSalary(),
            newEmployee.getSsn(),
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

    // Delete Employee by ID
    public void delete(Long EmpID){
        Employees delEmp = employeesRepo.findById(EmpID).orElseThrow(()-> new EntityNotFoundException("Employee not found with EmpID " + EmpID));
        employeesRepo.delete(delEmp);
    }

    // Update Employees
    public Employees updateEmployee(Long id,Employees employee){
        // Find the existing employee, otherwise throw error
        Employees existing = employeesRepo.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));

        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmail(employee.getEmail());
        existing.setHireDate(employee.getHireDate());
        existing.setSalary(employee.getSalary());
        existing.setSsn(employee.getSsn());
        existing.setAddress(employee.getAddress());
        existing.setGender(employee.getGender());
        existing.setIdentifiedRace(employee.getIdentifiedRace());
        existing.setDob(employee.getDob());
        existing.setPhone(employee.getPhone());
        
        return employeesRepo.save(existing);
    }

    //Search for an employee using name, DOB, SSN, empid to show their information for editing
    //(Admin only)
    public List<Employees> searchEmployees(String firstName, String lastName, String ssn, Long empid) {
        Employees searchFields = new Employees();
        searchFields.setFirstName(firstName);
        searchFields.setLastName(lastName);
        searchFields.setSsn(ssn);
        searchFields.setEmpid(empid);
        
        // Setting Custom Queries with JPA's ExampleMatcher
        // This is for searchEmployees
        ExampleMatcher SearchCondition_MatchAll = ExampleMatcher
            .matchingAll()            // Basically finds a match between all provided fields (field1 AND field2)
            .withIgnoreCase()        // Case-insensitive
            .withIgnoreNullValues() // Ignore empty fields
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Uses LIKE

        /*
        Basically this ExampleMatcher is like a template for a SQL query that follows the format
        SELECT * FROM TABLE
        WHERE LOWER(field1) LIKE %input1% for any number of fields provided
        We provide the search fields as firstName, secondName, ssn, and empid.
        Example.of(searchFields, SearchCondition_MatchAll) will return a query definition.
        employeesRepo then uses that query definition to find employees with the query.
        */


        Example<Employees> searchResult = Example.of(searchFields, SearchCondition_MatchAll); // get the results of the searchfield and the matching conditions
        return employeesRepo.findAll(searchResult); // Search the database 
    }
    
    // Takes a range from min to max salary range, finds the employees in that range and increase their salary by percent
    public void updateEmployeesSalary(double incPercent, double minSalary , double maxSalary){

        List<Employees> employees = employeesRepo.findBySalaryBetween(minSalary, maxSalary);

        for (Employees e : employees) {
            double updatedSalary = e.getSalary() * (1 + (incPercent / 100));
            e.setSalary(updatedSalary);
        }

        employeesRepo.saveAll(employees);
    }

}

/*
    Retired Methods

    These functions are no longer needed after implementing ExampleMatcher.


    public Employees getEmployeeById(long id){
        return employeesRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found with EmpId" + id));
    }

    public Employees getEmployeeBySSN(String ssn){
        return employeesRepo.findBySsn(ssn).orElseThrow(() -> new EntityNotFoundException("Employee not found with SSN:" + ssn));
    }
    
    public Employees getEmployeeByDOB(Date dob){
        return employeesRepo.findByDob(dob).orElseThrow(()-> new EntityNotFoundException("Employees not found with Date of Birth:" + dob));
    }

    public Employees getEmployeesByFirstName(String firstName){
        return employeesRepo.findByFirstName(firstName).orElseThrow(()-> new EntityNotFoundException("Employee not found with first name" + firstName));
    }

    public Employees getEmployeesByLastName(String lastName){
        return employeesRepo.findByLastName(lastName).orElseThrow(()-> new EntityNotFoundException("Employee not found with last name" + lastName));
    }
*/
