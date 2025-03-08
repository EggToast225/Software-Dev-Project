package com.example.sql_dbms_ui.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class JobTitle {
    @Column
    private String jobTitleName;

    @Id
    @OneToOne // One jobTitleID applies to one Job Title (Foreign key)
    @JoinColumn(name ="jobTitleID")
    private EmployeeJobTitle employeeJobTitle;

    //getters and setters
    public String getjobTitleName(){return jobTitleName;}
    public void setjobTitleName(String jobTitleName){this.jobTitleName = jobTitleName;}
}
