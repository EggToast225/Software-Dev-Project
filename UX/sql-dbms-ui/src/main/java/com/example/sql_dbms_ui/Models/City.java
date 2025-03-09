package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cityID;

    @Column(unique = true)
    @NotNull
    private String cityName;

    //getters and setters
    public long getCityID(){return cityID;}
    public void setCityID(long cityID){this.cityID = cityID;}

    public String getCityName(){return cityName;}
    public void setCityName(String cityName){this.cityName = cityName;}
}
