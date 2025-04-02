package com.example.sql_dbms_ui.repo;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sql_dbms_ui.Models.Employees;





public interface EmployeesRepo extends JpaRepository<Employees, Long>{

    // Custom Queries
    Optional<Employees> findBySSN(String ssn);
    Optional<Employees> findByDOB(Date dob);

    Optional<Employees> findByFirstName(String firstName);
    Optional<Employees> findByLastName(String lastName);


}