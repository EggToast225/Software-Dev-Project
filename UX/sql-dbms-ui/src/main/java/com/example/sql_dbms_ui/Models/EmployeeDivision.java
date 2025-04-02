package com.example.sql_dbms_ui.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class EmployeeDivision{
    @Id
    @Column(name = "empid")
    private Long empId;

    @OneToOne
    @JoinColumn(name = "EmpID") //One employeeID per Employee (Foreign Key)
    private Employees employee;

    @ManyToOne
    @JoinColumn(name = "div_ID")
    private Division division;

    // Getters and setters
    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public Employees getEmployee(){return employee;}
    public void setEmployee(Employees employee){
        this.employee = employee;
        /*if (employee != null){
        employee.setEmployeeDivision(this);
        }*/
    }
}
