

import com.example.sql_dbms_ui.Services.EmployeeServices;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;
import java.util.Date;

import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.repo.EmployeesRepo;
import com.example.sql_dbms_ui.repo.PayrollRepo;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EmployeeServicesTest {

    @Mock
    private EmployeesRepo employeesRepo;

    @Mock
    private PayrollRepo payrollRepo;

    @InjectMocks
    private EmployeeServices employeeServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEmployeeById_WhenEmployeeExists_ReturnsEmployee() {
        // Arrange
        Employees mockEmployee = new Employees();
        mockEmployee.setEmpid(1L);
        mockEmployee.setFirstName("John");
        mockEmployee.setLastName("Doe");
        mockEmployee.setEmail("john.doe@example.com");
        mockEmployee.setHireDate(new Date());

        when(employeesRepo.findById(1L)).thenReturn(Optional.of(mockEmployee));

        // Act
        Employees found = employeeServices.getEmployeeById(1L);
        System.out.println("Employee retrieved: " + found.getFirstName() + " " + found.getLastName());

        // Assert
        assertNotNull(found);
        assertEquals("John", found.getFirstName());
        assertEquals("Doe", found.getLastName());
        verify(employeesRepo, times(1)).findById(1L);

        
    }

    @Test
    public void testGetEmployeeById_WhenEmployeeDoesNotExist_ThrowsException() {
        // Arrange
        when(employeesRepo.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            employeeServices.getEmployeeById(99L);
        });

        assertTrue(thrown.getMessage().contains("Employee not found with EmpId"));
        verify(employeesRepo, times(1)).findById(99L);
    }
}