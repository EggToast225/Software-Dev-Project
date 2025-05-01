package com.example.sql_dbms_ui.Services.Interfaces;

import java.util.List;

import com.example.sql_dbms_ui.Models.Employees;


public interface AdminInterface extends Interface {
    public void createUser(Employees newEmployee);
    public void delete(Long empId);
    public Employees updateEmployee(Long id, Employees employee);
    public List<Employees> searchEmployees(String firstName, String lastName, String ssn, Long empid);
    public void updateEmployeesSalary(double incPercent, double minSalary, double maxSalary);
}