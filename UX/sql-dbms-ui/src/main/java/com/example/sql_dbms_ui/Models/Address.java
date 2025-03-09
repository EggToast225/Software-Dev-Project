package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Address {

    // Primary Key
    @Id
    private long EmpID;

    @Column
    private String street;

    @Column
    private int zipcode;

    
    @OneToOne                        //One Employee has one address
    @MapsId                         // Allows a primary key from another table to be used; this mapping is better than using @JoinColumn on the Primary Key (JPA requires all tables to have a primary key)
    @JoinColumn(name = "EmpId")    // Foreign key that references Employee, names the column EmpId
    private Employees employee;

    @OneToOne
    @JoinColumn(name = "stateID")
    private State state;    // Foreign key that references State's primary key, MapsID can be used for one column

    @OneToOne
    @JoinColumn(name = "cityID")
    private City city;      //Foreign key that references City's primary key

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

    public City getCity(){return city;}
    public void setCity(City city){this.city = city;}
    
}
