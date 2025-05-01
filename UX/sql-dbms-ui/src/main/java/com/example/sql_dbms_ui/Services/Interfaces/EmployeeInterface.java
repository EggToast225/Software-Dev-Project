package com.example.sql_dbms_ui.Services.Interfaces;

import com.example.sql_dbms_ui.Models.Employees;

public interface EmployeeInterface extends Interface {
    public Employees getEmployeeById(long id);
}
