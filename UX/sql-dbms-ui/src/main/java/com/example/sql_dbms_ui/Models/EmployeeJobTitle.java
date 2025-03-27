// EmployeeJobTitle.java (Updated to match DB schema)
package com.example.sql_dbms_ui.Models;

import jakarta.persistence.*;

@Entity
public class EmployeeJobTitle {

    @Id
    @Column(name = "empid")
    private int empId;

    @OneToOne
    @JoinColumn(name = "empid", insertable = false, updatable = false)
    private Employees employee;

    @ManyToOne
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;

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

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }
}
