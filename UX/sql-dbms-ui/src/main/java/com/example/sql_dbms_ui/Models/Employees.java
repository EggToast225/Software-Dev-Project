package com.example.sql_dbms_ui.Models;

import java.util.Date;

@Entity
public class Employees {

    @Id
    @Column(name = "empid")
    private Long empid;

    @Column(name = "Fname")
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;
    
    @Column
    private Date HireDate;

    @Column
    private float Salary;

    @Column(name = "SSN", unique=true)
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
    public Long getEmpid() {
        return empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }

    public String getFirstName(){ return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getLastName(){return lastName;}
    public void setLastName(String lastName){this.lastName = lastName;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email  = email;}

    /*
    public Division getDivision(){return division;}
    public void setDivision(Division division){this.division = division;}

    public Address getAddress(){return address;}
    public void setAddress(Address address){
        this.address = address;
        if (address != null){
        address.setEmployee
    */
    public Date getHireDate(){return HireDate;}
    public void setHireDate(Date HireDate){this.HireDate = HireDate;}

    public float getSalary(){return Salary;}
    public void setSalary(float Salary){this.Salary = Salary;}

    public int getSSN(){return SSN;}
    public void setSSN(int SSN){this.SSN = SSN;}
}
