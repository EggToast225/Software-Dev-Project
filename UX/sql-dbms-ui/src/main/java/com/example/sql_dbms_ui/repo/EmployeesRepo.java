package com.example.sql_dbms_ui.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sql_dbms_ui.Models.Employees;

public interface EmployeesRepo extends JpaRepository<Employees,Long>{
}