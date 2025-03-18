package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Division {
    // Primary Key
    @Id
    private long EmpID;

    @Column
    private String name;

    @Column
    private String street;

    @Column
    private int postal_code;
    
    @OneToOne                        //One Employee has one address
    @MapsId                         // Allows a primary key from another table to be used; this mapping is better than using @JoinColumn on the Primary Key (JPA requires all tables to have a primary key)
    @JoinColumn(name = "EmpId")    // Foreign key that references Employee, names the column EmpId
    private EmployeeDivision employeeDivision;

    @OneToOne
    @JoinColumn(name = "State")
    private State state;    // Foreign key that references State's primary key, MapsID can be used for one column

    @OneToOne
    @JoinColumn(name = "City")
    private City city;      //Foreign key that references City's primary key

    @OneToOne
    @JoinColumn(name = "Country")
    private Country country;

    // getters and setters
    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public long getEmpID(){return EmpID;}
    public void setEmpID(long EmpID){this.EmpID = EmpID;}

    public EmployeeDivision getEmployee(){return employeeDivision;}
    public void setEmployee(EmployeeDivision employeeDivision){this.employeeDivision = employeeDivision;}

    public String getStreet() {return street;}
    public void setAddress(String street){this.street = street;}

    public int getZipCode() {return postal_code;}
    public void setZipCode(int zipcode){this.postal_code = zipcode;}

    public State getState(){return state;}
    public void setState(State state){this.state = state;}

    public City getCity(){return city;}
    public void setCity(City city){this.city = city;}

    public Country getCountry(){return country;}
    public void setCountry(Country country){this.country = country;}
}
