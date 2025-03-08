package com.example.sql_dbms_ui.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Employee_Division{
    @Id
    @OneToOne
    @JoinColumn(name = "EmpID")
    private Employees employee;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected int divisionID;

}
