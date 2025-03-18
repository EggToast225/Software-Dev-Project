package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class State { 

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long stateID;

    @Column
    private String stateName;

    //getters and setters
    public long getStateID(){return stateID;}
    public void setStateID(long stateID){this.stateID = stateID;}
    
    public String getStateName(){return stateName;}
    public void setStateName(String stateName){this.stateName = stateName;}
}
