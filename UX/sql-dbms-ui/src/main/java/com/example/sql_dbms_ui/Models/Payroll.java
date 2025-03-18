package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
/*
 * Payroll Table
 * Columns:
 * long payID
 * float earnings
 * float fed_tax
 * float fed_med
 * float fed_SS
 * float state_tax
 * float retire_401k
 * float healthcare
 * 
 * Foriegn key:
 * OneToOne
 * Employees employee
 */
@Entity
@Table(name = "payroll")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long payID;

    @Column
    private Date pay_date;
    
    @Column
    private float earnings;
    
    @Column
    private float fed_tax;

    @Column
    private float fed_med;

    @Column
    private float fed_SS;

    @Column
    private float state_tax;

    @Column
    private float retire_401k;

    @Column 
    private float health_care;

    @MapsId
    @OneToOne
    @JoinColumn (name = "EmpID")
    private Employees employee;
    
    //getters and setters

    public long getPayID() {return payID;}
    public void setPayID(long payID) {this.payID = payID;}

    public Date getPay_date() {return pay_date;}
    public void setPay_date(Date pay_date) {this.pay_date = pay_date;}

    public float getEarnings() {return earnings;}
    public void setEarnings(float earnings) {this.earnings = earnings;}

    public float getFed_tax() {return fed_tax;}
    public void setFed_tax(float fed_tax) {this.fed_tax = fed_tax;}

    public float getFed_med() {return fed_med;}
    public void setFed_med(float fed_med) {this.fed_med = fed_med;}

    public float getFed_SS() {return fed_SS;}
    public void setFed_SS(float fed_SS) {this.fed_SS = fed_SS;}

    public float getState_tax() {return state_tax;}
    public void setState_tax(float state_tax) {this.state_tax = state_tax;}

    public float getRetire_401k() {return retire_401k;}
    public void setRetire_401k(float retire_401k) {this.retire_401k = retire_401k;}

    public float getHealth_care() {return health_care;}
    public void setHealth_care(float health_care) {this.health_care = health_care;}

    public Employees getEmployee() {return employee;}
    public void setEmployee(Employees employee) {this.employee = employee;}
}
