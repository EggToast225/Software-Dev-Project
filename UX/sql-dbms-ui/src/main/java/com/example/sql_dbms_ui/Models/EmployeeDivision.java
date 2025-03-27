// EmployeeDivision.java (Updated to match DB schema)
package com.example.sql_dbms_ui.Models;

import jakarta.persistence.*;

@Entity
public class EmployeeDivision {

    @Id
    @Column(name = "empid")
    private int empId;

    @OneToOne
    @JoinColumn(name = "empid", insertable = false, updatable = false)
    private Employees employee;

    @ManyToOne
    @JoinColumn(name = "div_ID")
    private Division division;

    // Getters and setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
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