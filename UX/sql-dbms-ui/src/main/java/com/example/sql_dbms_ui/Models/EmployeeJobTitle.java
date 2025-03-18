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

    // getters and setters
    public long getJobTitleID(){return jobTitleID;}
    public void setJobTitleID(long jobTitleID){this.jobTitleID = jobTitleID;}

    public Employees getEmployee() {return employee;}
    public void setEmployee(Employees employee) {this.employee = employee;}
}
