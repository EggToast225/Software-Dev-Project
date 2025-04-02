package com.example.sql_dbms_ui.Services;

import java.util.Date;

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
    

    public void updateEmployeeSalary(String searchMethod, String searchValue, double newSalary){
        try { // May result in an error if provided searchMethod is not valid
            
            // This comment explains what the implemented code tries to do; we can use severals way to search an employee, so we can pass a methodName then invoke with reflection
            // This alows for a method to be ran by name dynamically

            Class<?> class_obj = Class.forName("AdminServices");                                        // Get the Class object for the class that contains the method
            Method searchFunction = class_obj.getMethod(searchMethod, String.class);            // Get the method object we want to invoke
            Object obj = Class.forName("AdminServices").getDeclaredConstructor().newInstance();         // Create a new instance of the class
            Optional<Employees> searchResult = (Optional<Employees>) searchFunction.invoke(obj, searchValue);     // Invoke the method on the instance, passing the parameter


            Employees employee = searchResult.orElseThrow(()-> new EntityNotFoundException("Employee not found"));
            employee.setSalary(newSalary); // Set the Employee's new salary
            employeesRepo.save(employee); // Persist the change.
        
        }
        catch (NoSuchMethodException e){
            throw new IllegalArgumentException("Invalid search method:" + searchMethod);
        }
        catch (Exception e){
            throw new RuntimeException("Error invoking search method: " + e.getMessage());
        }
    }
}
