package com.example.sql_dbms_ui.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sql_dbms_ui.Models.Employees;





public interface EmployeesRepo extends JpaRepository<Employees, Long>{
    
    
    List<Employees> findBySalaryBetween(double min, double max);



    /* 
    // Custom Queries
    Optional<Employees> findBySsn(String ssn);
    Optional<Employees> findByDob(Date dob);
    Optional<Employees> findByEmail(String email);

    Optional<Employees> findByFirstName(String firstName);
    Optional<Employees> findByLastName(String lastName);

    
    // Query for searching up an employee with name, ssn, dob, and id
    @Query("SELECT e FROM Employees e " +
            "WHERE (:name IS NULL OR CONCAT(e.firstName, ' ', e.lastName) LIKE %:name%) " +
            "AND (:ssn IS NULL OR e.ssn LIKE %:ssn%) " +
            "AND (:dob IS NULL OR e.dob = :dob) " +
            "AND (:empid IS NULL OR e.empid = :empid)")

    List<Employees> searchEmployees(
        @Param("firstName") String firstName,
        @Param("lastName") String lastName,
        @Param("dob") Date dob,
        @Param("ssn") String ssn,
        @Param("empid") Long empid
    );
*/
    }