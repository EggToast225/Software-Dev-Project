// EmployeeDivision.java (Updated to match DB schema)
package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class EmployeeDivision {

    @Id
    @Column(name = "empid")
    private Long empId;

    @OneToOne
    @JoinColumn(name = "empid", insertable = false, updatable = false)
    @MapsId
    private Employees employee;

    @ManyToOne
    @JoinColumn(name = "div_ID")
    private Division division;

    // Getters and setters
    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }
}