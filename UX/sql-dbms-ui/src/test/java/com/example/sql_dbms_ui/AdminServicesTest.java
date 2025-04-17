package com.example.sql_dbms_ui;

import com.example.sql_dbms_ui.Services.AdminServices;
import com.example.sql_dbms_ui.repo.EmployeesRepo;
import com.example.sql_dbms_ui.repo.PayrollRepo;
import com.example.sql_dbms_ui.Models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;


public class AdminServicesTest {

    @Mock
    private EmployeesRepo employeesRepo;

    @Mock
    private PayrollRepo payrollRepo;

    @InjectMocks
    private AdminServices adminServices;

    private Employees testEmployee;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        City city = new City();
        city.setCityName("JohnDoeVille");

        State state = new State();
        state.setStateName("Jadoah");
        
        // Setting up Address
        
        Address testAddress = new Address();

        testAddress.setCity(city);
        testAddress.setState(state);
        testAddress.setStreet("johndoeville 123");
        testAddress.setZip("12345");


        // Setting up Employee
        testEmployee = new Employees();
        testEmployee.setEmpid(1L);
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setEmail("john.doe@example.com");
        testEmployee.setSalary(50000);
        testEmployee.setSSN("123-45-6789");
        testEmployee.setAddress(testAddress);
        testEmployee.setGender("Male");
        testEmployee.setIdentifiedRace("Other");
        testEmployee.setDob(new Date());
        testEmployee.setPhone("123-456-7890");
    }

    @Test
    void testCreateUser(){
        adminServices.createUser(testEmployee);
        verify(employeesRepo, times(1)).save(any(Employees.class));
    }

    // Get a list of employees
    // adminServices returns list of employees
    // check to see if testEmployee is inside list
    // assertEqual size of list to 1;
    // assertEqual first employee from the list name is "John"
    @Test
    void testGetAllEmployees(){
        when(employeesRepo.findAll()).thenReturn(List.of(testEmployee));
        List<Employees> result =  adminServices.getAllEmployees();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    // Get employee from their corresponding id
    // Test to see if employee matches with testEmployee's first name and last name
    @Test
    void testGetEmployeeById(){
        when(employeesRepo.findById(1L)).thenReturn(Optional.of(testEmployee));
        Employees found = adminServices.getEmployeeById(1L);
        assertEquals("John", found.getFirstName());
        assertEquals("Doe", found.getLastName());
    }

    // See if a EntityNotFoundException is thrown as it is expected
    // () -> is a lambda expression that execute the function so that assertThrow catches it
    // Test is to see if it throws that specific exception
    @Test
    void testGetEmployeeByIdNotFound(){
        when(employeesRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> adminServices.getEmployeeById(1L));
    }


    // See if updateEmployeeSalary updates selected employee's salary
    @Test
    void testUpdateEmployeeSalary() {
        when(employeesRepo.findById(1L)).thenReturn(Optional.of(testEmployee));
        adminServices.updateEmployeeSalary(1L, 70000);
        assertEquals(70000, testEmployee.getSalary());
        verify(employeesRepo, times(1)).save(testEmployee);
    }

}
