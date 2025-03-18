package com.example.sql_dbms_ui.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.util.Date;

import org.springframework.format.annotation.NumberFormat;

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
    private Date dateOfBirth;

    @Column
    @NumberFormat(pattern = "###-###-####")
    private int phoneNum;
    
    //getters and setters

    public Employees getEmployee(){return employee;}
    public void setEmployee(Employees employee){this.employee = employee;}

    public String getGender(){return gender;}
    public void setGender(String gender){this.gender = gender;}

    public String getRace(){return race;}
    public void setRace(String race){this.race = race;}

    public Date getDateOfBirth(){return dateOfBirth;}
    public void setDateOfBirth(Date dateofBirth){this.dateOfBirth = dateofBirth;}

    public int getPhoneNum(){return phoneNum;}
    public void setPhoneNum(int phoneNum){this.phoneNum = phoneNum;}
}
