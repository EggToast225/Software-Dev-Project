package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
@Entity
public class EmployeeJobTitle {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long jobTitleID;

    
    @OneToOne
    @JoinColumn(name = "EmpID") //One employeeID per Employee (Foreign Key)
    private Employees employee;

    @OneToOne()

    // getters and setters
    public long getJobTitleID(){return jobTitleID;}
    public void setJobTitleID(long jobTileID){this.jobTitleID = jobTitleID;}
}
