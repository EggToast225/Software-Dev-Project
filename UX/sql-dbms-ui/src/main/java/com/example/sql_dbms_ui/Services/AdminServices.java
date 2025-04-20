package com.example.sql_dbms_ui.Services;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public void delete(Long EmpID){
        Employees delEmp = employeesRepo.findById(EmpID).orElseThrow(()-> new EntityNotFoundException("Employee not found with EmpID " + EmpID));
        employeesRepo.delete(delEmp);
    }

    
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


    // Setting Custom Queries with JPA's Example matcher
    private static final ExampleMatcher SEARCH_CONDITION_MATCH_ANY = ExampleMatcher
        .matchingAll()
        .withIgnoreCase()
        .withIgnoreNullValues()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    /*
    Basically this is saying that the ExampleMatcher matches with anything containing to the string equivalent of the values given
    Works by providing a employee class and then getting an Example<Employees> with those matching values
    .withIgnoreNullValues is included so that it null Values don't count towards matching
    
        */
    
    public List<Employees> searchEmployees(String firstName, String lastName, String ssn, Long empid) {
        Employees searchFields = new Employees();
        searchFields.setFirstName(firstName);
        searchFields.setLastName(lastName);
        searchFields.setSsn(ssn);
        searchFields.setEmpid(empid);
        
        Example<Employees> searchResult = Example.of(searchFields, SEARCH_CONDITION_MATCH_ANY);
        return employeesRepo.findAll(searchResult);
    }

    

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
    

    public void updateEmployeeSalary(Long id ,double newSalary){
        Employees employee = employeesRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found with EmpId " + id));
        employee.setSalary(newSalary);
        employeesRepo.save(employee);
    }
}
