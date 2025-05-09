package com.example.sql_dbms_ui.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sql_dbms_ui.Models.Employees;





public interface EmployeesRepo extends JpaRepository<Employees, Long>{
    Optional<Employees> findByEmail(String email);
    
    // Custom Queries
    /*  There a cool thing that JPA does that makes this work.
        findBySalaryBetween(double min, double max) will look for keywords in the method
        so something like 'findBy' will do SELECT
        'Salary' will refer to e.salary
        'Between' will do a range within the given parameters

        So the equivalent query that this does is

        SELECT Employees e
        WHERE e.salary BETWEEN min AND max
    */
    List<Employees> findBySalaryBetween(double min, double max);

    @Query(value = "SELECT jt.title FROM job_title jt " +
                  "JOIN employee_job_title ejt ON jt.job_title_id = ejt.job_title_id " +
                  "WHERE ejt.empid = :employeeId ",
           nativeQuery = true)
    String findCurrentJobTitleByEmployeeId(@Param("employeeId") Long employeeId);
}