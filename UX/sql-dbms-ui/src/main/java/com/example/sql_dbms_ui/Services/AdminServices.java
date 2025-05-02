package com.example.sql_dbms_ui.Services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.example.sql_dbms_ui.Models.Address;
import com.example.sql_dbms_ui.Models.City;
import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.Models.Payroll;
import com.example.sql_dbms_ui.Models.State;
import com.example.sql_dbms_ui.Services.DTO.EmployeeDTO;
import com.example.sql_dbms_ui.Services.Interfaces.AdminInterface;
import com.example.sql_dbms_ui.repo.AddressRepo;
import com.example.sql_dbms_ui.repo.CityRepo;
import com.example.sql_dbms_ui.repo.EmployeesRepo;
import com.example.sql_dbms_ui.repo.PayrollRepo;
import com.example.sql_dbms_ui.repo.StateRepo;

import jakarta.persistence.EntityNotFoundException;

// Business Logic for Admin side, part of service layer

@Service
public class AdminServices implements AdminInterface{

    private final EmployeesRepo employeesRepo;
    private final PayrollRepo   payrollRepo;
    private final CityRepo      cityRepo;
    private final StateRepo     stateRepo;
    private final AddressRepo   addressRepo;

    public AdminServices(EmployeesRepo employeesRepo, PayrollRepo payrollRepo, CityRepo cityRepo, StateRepo stateRepo, AddressRepo addressRepo){
        this.employeesRepo  = employeesRepo;
        this.payrollRepo    = payrollRepo;
        this.cityRepo       = cityRepo;
        this.stateRepo      = stateRepo;
        this.addressRepo    = addressRepo;
    }

    
    // Create new user then stores it in database
    public void createUser(EmployeeDTO dto) {
        Employees employee = new Employees();
        employee.setFirstName(dto.firstName);
        employee.setLastName(dto.lastName);
        employee.setEmail(dto.email);
        
        // Convert string dates to java.sql.Date
        if (dto.hireDate != null && !dto.hireDate.trim().isEmpty()) {
            employee.setHireDate(java.sql.Date.valueOf(dto.hireDate));
        }
        
        employee.setSalary(dto.salary);
        employee.setSsn(dto.ssn);
        employee.setGender(dto.gender);
        employee.setIdentifiedRace(dto.identifiedRace);
        
        if (dto.dob != null && !dto.dob.trim().isEmpty()) {
            employee.setDob(java.sql.Date.valueOf(dto.dob));
        }
        
        employee.setPhone(dto.phone);

        // Address logic
        City city = cityRepo.findByCityName(dto.address.cityName).orElseGet(()-> {
            City newCity = new City();
            newCity.setCityName(dto.address.cityName);
            return cityRepo.save(newCity);
        });

        State state = stateRepo.findByStateName(dto.address.stateName).orElseGet(()->{
            State newState = new State();
            newState.setStateName(dto.address.stateName);
            return stateRepo.save(newState);
        });

        Address address = new Address();
        address.setStreet(dto.address.street);
        address.setZip(dto.address.zip);
        address.setCity(city);
        address.setState(state);
        addressRepo.save(address);

        employee.setAddress(address);
        employeesRepo.save(employee);
    }


    // Get all Employees
    public List<Employees> getAllEmployees(){
        return employeesRepo.findAll();
    }

    // Delete Employee by ID
    @Override
    public void delete(Long EmpID){
        Employees delEmp = employeesRepo.findById(EmpID)
        .orElseThrow(()-> new EntityNotFoundException("Employee not found with EmpID " + EmpID));
        employeesRepo.delete(delEmp);
    }

    // Update Employees
    @Override
    public Employees updateEmployee(Long id,Employees employee){
        // Find the existing employee, otherwise throw error
        Employees existing = employeesRepo.findById(id)
        .orElseThrow(()-> new RuntimeException("Employee not found"));

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
    
    /*
        Basically this ExampleMatcher is like a template for a SQL query that follows the format
        SELECT * FROM TABLE
        WHERE LOWER(field1) LIKE %input1% for any number of fields provided
        We provide the search fields as firstName, secondName, ssn, and empid.
        Example.of(searchFields, SearchCondition_MatchAll) will return a query definition.
        employeesRepo then uses that query definition to find employees with the query.
    */
    
    @Override
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
            .withIgnorePaths("salary")
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Uses LIKE

        Example<Employees> searchQuery = Example.of(searchFields, SearchCondition_MatchAll); // get example query
        return employeesRepo.findAll(searchQuery); // Search the database with example query
    }


    // Takes a range from min to max salary range, finds the employees in that range and increase their salary by percent
    @Override
    public void updateEmployeesSalary(double incPercent, double minSalary , double maxSalary){

        List<Employees> employees = employeesRepo.findBySalaryBetween(minSalary, maxSalary);

        for (Employees e : employees) {
            double updatedSalary = e.getSalary() * (1 + (incPercent / 100));
            e.setSalary(updatedSalary);
        }

        employeesRepo.saveAll(employees);
    }
    
    // Provides a list of employees found within a range from min to max salary range
    public List<Employees> findEmployeesBetweenSalary(double minSalary, double maxSalary){
        return employeesRepo.findBySalaryBetween(minSalary, maxSalary);
    }


    // getPayroll
    @Override
    public Payroll getPayrollById(long id){
        return payrollRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Employee's payroll not found with Id " + id));
    }

    // get Monthly Earnings by JobTitle
    public Map<String,Double> getTotalMonthlyEarningsByJobTitle(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // Set to first day of selected month
        cal.set(year, month - 1, 1, 0, 0, 0);
        Date startDate = cal.getTime();
        
        // Set to last day of selected month
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date endDate = cal.getTime();

        List<Map<String, Object>> results = payrollRepo.findTotalMonthlyEarningsByJobTitle(startDate, endDate);
        
        // Include all job titles, even those with null or zero earnings
        return results.stream()
            .collect(Collectors.toMap(
                result -> (String) result.get("jobTitle"),
                result -> result.get("earnings") != null ? ((Number)result.get("earnings")).doubleValue() : 0.0
            ));
    }

    public Map<String,Double> getTotalMonthlyEarningsByDivision(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // Set to first day of selected month
        cal.set(year, month - 1, 1, 0, 0, 0);
        Date startDate = cal.getTime();
        
        // Set to last day of selected month
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date endDate = cal.getTime();

        List<Map<String, Object>> results = payrollRepo.findTotalMonthlyEarningsByDivision(startDate, endDate);

        return results.stream()
            .filter(result -> result.get("earnings") != null && ((Number)result.get("earnings")).doubleValue() > 0)
            .collect(Collectors.toMap(
                result -> (String) result.get("division"),
                result -> ((Number) result.get("earnings")).doubleValue()
            ));
    }

    public List<Payroll> findAllByOrderByEmployeeEmpidAscPayDateAsc(){
        return payrollRepo.findAllByOrderByEmployeeEmpidAscPayDateAsc();
    }

    public List<Payroll> findByEmployeeEmpidOrderByPayDateAsc(Long empid){
        return payrollRepo.findByEmployeeEmpidOrderByPayDateAsc(empid);
    }

    public List<Payroll> findByEmployeeEmpidOrderByPayDateDesc(Long empid){
        return payrollRepo.findByEmployeeEmpidOrderByPayDateDesc(empid);
    }
}