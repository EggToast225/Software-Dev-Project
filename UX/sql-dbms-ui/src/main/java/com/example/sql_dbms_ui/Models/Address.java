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
    private long EmpID;

    @Column
    private String street;

    @Column
    private int zipcode;

    
    @OneToOne
    @MapsId
    @JoinColumn(name = "EmpId") // Foreign key
    private Employees employee;

    @OneToOne
    @JoinColumn(name = "stateID")
    private State state;  // Foreign key

    @OneToOne
    @JoinColumn(name = "cityID")
    private City city;

    // getters and setters
    public long getEmpID(){return EmpID;}
    public void setEmpID(long EmpID){this.EmpID = EmpID;}

    public Employees getEmployee(){return employee;}
    public void setEmployee(Employees employee){this.employee = employee;}

    public String getStreet() {return street;}
    public void setAddress(String street){this.street = street;}

    public int getZipCode() {return zipcode;}
    public void setZipCode(int zipcode){this.zipcode = zipcode;}

    public State getState(){return state;}
    public void setState(State state){this.state = state;}
    
}
