package com.example.sql_dbms_ui.Services;

import java.util.Date;
import java.util.List;
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

    // Create new user then stores it in database
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

    public void delete(Long EmpID){
        Employees delEmp = employeesRepo.findById(EmpID).orElseThrow(()-> new EntityNotFoundException("Employee not found with EmpID " + EmpID));
        employeesRepo.delete(delEmp);
    }

    
    public void updateEmployee(Employees employee){
        // Find the existing options of employee
        Optional<Employees> existOpt = employeesRepo.findById(employee.getEmpid());

        if (existOpt.isPresent()){
            Employees existing = existOpt.get();

            existing.setFirstName(employee.getFirstName());
            existing.setLastName(employee.getLastName());
            existing.setEmail(employee.getEmail());
            existing.setHireDate(employee.getHireDate());
            existing.setSalary(employee.getSalary());
            existing.setSSN(employee.getSSN());
            existing.setAddress(employee.getAddress());
            existing.setGender(employee.getGender());
            existing.setIdentifiedRace(employee.getIdentifiedRace());
            existing.setDob(employee.getDob());
            existing.setPhone(employee.getPhone());
        }


        employeesRepo.save(employee);
    }

    //Search for an employee using name, DOB, SSN, empid to show their information for editing
    //(Admin only)

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
