package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Country {

    @Id
    private long countryID;

    @Column
    private String countryName;

    //getters and setters

    public long getcountryID(){return countryID;}
    public void setcountryID(long countryID){this.countryID = countryID;}

    public String getcountryName(){return countryName;}
    public void setcountryName(String countryName){this.countryName = countryName;}
}
