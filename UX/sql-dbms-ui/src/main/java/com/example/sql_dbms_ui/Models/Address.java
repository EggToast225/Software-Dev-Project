package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Address {
    @Id
    private long ID;

    @Column
    private String Name;

    @OneToOne
    @MapsId
    @JoinColumn(name = "EmpID")
    private Employees employee;

    //getters and setters

    public long getID() {return ID;}
    public void setID(long iD) {ID = iD;}
    
    public String getName() {return Name;}
    public void setName(String name){this.Name = name;}

    public Employees getEmployee() {return employee;}
    public void setDivision(Employees employee) {this.employee = employee;}
}
