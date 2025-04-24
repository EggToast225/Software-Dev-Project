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

import org.hibernate.persister.entity.mutation.UpdateCoordinator;
import org.springframework.data.domain.Example;

import com.example.sql_dbms_ui.Services.EmployeeServices;

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
        testEmployee.setSsn("123-45-6789");
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

    // Test searchEmployees with matching fields
    @Test
    void testSearchEmployees() {
        when(employeesRepo.findAll(any(Example.class))).thenReturn(List.of(testEmployee));

        List<Employees> result = adminServices.searchEmployees("John", "Doe", "123-45-6789", 1L);
        
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals(1, result.size());

    }

    // Test searchEmployees with no matches
    @Test
    void testSearchEmployeesNotFound() {
        when(employeesRepo.findAll()).thenReturn(List.of());
        List<Employees> result = adminServices.searchEmployees("Jane", "Smith", "000-00-0000", 99L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateEmployees(){
        Employees updatedEmployee = new Employees();

        updatedEmployee.setEmpid(2L);
        updatedEmployee.setFirstName("Bill");
        updatedEmployee.setLastName("Baskin");
        updatedEmployee.setEmail("billbask@email.com");
        updatedEmployee.setHireDate(new Date());
        updatedEmployee.setSalary(1234567890);
        updatedEmployee.setSsn("234-56-7891");
        updatedEmployee.setGender("Other");
        updatedEmployee.setIdentifiedRace(" ");
        updatedEmployee.setDob(new Date());
        updatedEmployee.setPhone("999-999-9999");

        
        // mock findById
        when(employeesRepo.findById(1L)).thenReturn(Optional.of(testEmployee)); //return test employee if findById(1L)
        // mock save
        when(employeesRepo.save(testEmployee)).thenReturn(testEmployee);

        Employees result = adminServices.updateEmployee(1L, updatedEmployee);

        assertEquals("Bill", result.getFirstName());
        assertEquals("Baskin", result.getLastName());
        assertEquals("billbask@email.com", result.getEmail());
        assertEquals(1234567890, result.getSalary());
        assertEquals("234-56-7891", result.getSsn());
        assertEquals("Other", result.getGender());
        assertEquals(" ", result.getIdentifiedRace());
        assertEquals("999-999-9999", result.getPhone());
    }

    @Test
    void testUpdateNotEmployees(){
        when(employeesRepo.findById(99L)).thenReturn(Optional.empty());

        Employees updatedEmployee = new Employees();
        updatedEmployee.setFirstName("not real");
        updatedEmployee.setLastName("fake");

        assertThrows(Exception.class, () -> adminServices.updateEmployee(99L, updatedEmployee));

    }

    // See if updateEmployeeSalary updates selected employee's salary
    @Test
    void testUpdateEmployeesSalary() {
        // Create 3 employees
        Employees emp1 = new Employees();
        emp1.setEmpid(1L);
        emp1.setSalary(60000);

        Employees emp2 = new Employees();
        emp2.setEmpid(2L);
        emp2.setSalary(80000);

        // Outside of Range
        Employees emp3 = new Employees();
        emp3.setEmpid(3L);
        emp3.setSalary(50000);

        // Should only be emp1 and emp2 as they in the salary range
        List<Employees> employeeList = List.of(emp1, emp2); 

        // Mock the repository to return employees in the specified salary range
        when(employeesRepo.findBySalaryBetween(58000, 105000)).thenReturn(employeeList);

        // Call the method with 10% increase
        adminServices.updateEmployeesSalary(10.0, 58000, 105000);

        // Check that salaries are updated correctly
        assertEquals(66000, emp1.getSalary());
        assertEquals(88000, emp2.getSalary());
        assertEquals(50000, emp3.getSalary());

        // Verify that saveAll was called with the modified list
        verify(employeesRepo, times(1)).saveAll(employeeList);
    }

}
