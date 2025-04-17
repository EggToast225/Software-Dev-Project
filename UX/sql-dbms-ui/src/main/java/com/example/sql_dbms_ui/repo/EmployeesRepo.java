package com.example.sql_dbms_ui.repo;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sql_dbms_ui.Models.Employees;





public interface EmployeesRepo extends JpaRepository<Employees, Long>{

    // Custom Queries
    Optional<Employees> findBySsn(String ssn);
    Optional<Employees> findByDob(Date dob);

    Optional<Employees> findByFirstName(String firstName);
    Optional<Employees> findByLastName(String lastName);


}