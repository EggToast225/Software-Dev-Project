package com.example.sql_dbms_ui.Models;

import java.text.SimpleDateFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Demographic{
    
    @Id
    @OneToOne
    @JoinColumn(name = "EmpID", referencedColumnName= "EmpID")
    private Employees employee;


    @Column
    private String gender;

    @Column
    private String race;

    @Column
    private SimpleDateFormat dateOfBirth;

    @Column
    private int phoneNum;

}
