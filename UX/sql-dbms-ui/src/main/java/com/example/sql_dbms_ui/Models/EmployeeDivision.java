package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class EmployeeDivision{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "EmpID") //One employeeID per Employee (Foreign Key)
    private Employees employee;

    //getter and setters

    public long getID(){return id;}
    public void setID(long id){this.id = id;}
    
    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public Employees getEmployee(){return employee;}
    public void setEmployee(Employees employee){
        this.employee = employee;
        /*if (employee != null){
        employee.setEmployeeDivision(this);
        }*/
    }
}
