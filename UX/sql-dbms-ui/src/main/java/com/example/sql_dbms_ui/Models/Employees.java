// Employees.java (Updated to match DB schema)
package com.example.sql_dbms_ui.Models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empid")
    private Long empid;

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
    public Address address;

    @Column(name = "gender")
    private String gender;

    @Column(name = "identified_race")
    private String identifiedRace;

    @Column(name = "DOB")
    private Date dob;

    @Column(name = "phone")
    private String phone;




    // Constructor for object
    public Employees(){}

    public Employees(String firstName, String lastName, String email, Date hireDate, double salary, String ssn, String gender, String identifiedRace, Date dob, String phone){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email =  email;
        this.hireDate = hireDate;
        this.salary = salary;
        this.ssn = ssn;
        this.gender = gender;
        this.identifiedRace = identifiedRace;
        this.dob = dob;
        this.phone = phone;
    }

    // Getters and setters
    public Long getEmpid() {
        return empid;
    }

    public void setEmpid(Long empid) {
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