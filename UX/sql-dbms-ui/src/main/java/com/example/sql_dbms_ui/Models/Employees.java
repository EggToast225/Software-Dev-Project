// Employees.java (Updated to match DB schema)
package com.example.sql_dbms_ui.Models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Employees {

    @Id
    @Column(name = "empid")
    private int empid;

    @Column(name = "Fname")
    private String firstName;

    @Column(name = "Lname")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "HireDate")
    private Date hireDate;

    @Column(name = "Salary")
    private double salary;

    @Column(name = "SSN")
    private String ssn;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "gender")
    private String gender;

    @Column(name = "identified_race")
    private String identifiedRace;

    @Column(name = "DOB")
    private Date dob;

    @Column(name = "phone")
    private String phone;

    // Getters and setters
    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentifiedRace() {
        return identifiedRace;
    }

    public void setIdentifiedRace(String identifiedRace) {
        this.identifiedRace = identifiedRace;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
