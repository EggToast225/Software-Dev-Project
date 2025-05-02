// Payroll.java (Updated to match DB schema)
package com.example.sql_dbms_ui.Models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_date")
    private Date payDate;

    @Column
    private Double earnings;

    @Column
    private Double fed_tax;

    @Column
    private Double fed_med;

    @Column
    private Double fed_SS;

    @Column
    private Double state_tax;

    @Column
    private Double retire_401k;

    @Column(name = "health_care")
    private Double healthCare;

    @ManyToOne
    @JoinColumn(name = "empid", referencedColumnName = "empid")
    private Employees employee;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Double getEarnings() {
        return earnings;
    }

    public void setEarnings(Double earnings) {
        this.earnings = earnings;
    }

    public Double getFed_tax() {
        return fed_tax;
    }

    public void setFed_tax(Double fed_tax) {
        this.fed_tax = fed_tax;
    }

    public Double getFed_med() {
        return fed_med;
    }

    public void setFed_med(Double fed_med) {
        this.fed_med = fed_med;
    }

    public Double getFed_SS() {
        return fed_SS;
    }

    public void setFed_SS(Double fed_SS) {
        this.fed_SS = fed_SS;
    }

    public Double getState_tax() {
        return state_tax;
    }

    public void setState_tax(Double state_tax) {
        this.state_tax = state_tax;
    }

    public Double getRetire_401k() {
        return retire_401k;
    }

    public void setRetire_401k(Double retire_401k) {
        this.retire_401k = retire_401k;
    }

    public Double getHealthCare() {
        return healthCare;
    }

    public void setHealthCare(Double healthCare) {
        this.healthCare = healthCare;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }
}