package com.example.sql_dbms_ui.Models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/*This class is an entity/table called employees
that is a subclass of EmployeeID that inherits the EmpID primary key mapping

columns/attributes:

long EmpID  - Employee ID
String firstName - Employee's first name
String lastName - Employee's last name
String email (unique) - Employee's email
int jobTitleID - Employee's Job Title ID

Primary Keys:
EmpID
*/
@Entity // specifies this as an entity
public class Employees  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //Sets this as the primary key
    private long EmpID; // acts as primary key

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    // Access to Address table, Cascade makes it so that when EmpID is assigned, it also assigns it to Address's ID (they share the primary key)
    @OneToOne (mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;  

    /* JSON format; this is the format used for SQL injections
    
    {   
        "firstname" : "String",
        "lastName" : "String",
        "email" : "String",
        "address" :{
            "street" : "String",
            "zipcode" : int
        }
    }
    
    */

    // getters and setters
    public long getEmpID(){return EmpID;}
    public void setEmpID(long EmpID){this.EmpID = EmpID;}

    public String getFirstName(){ return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getLastName(){return lastName;}
    public void setLastName(String lastName){this.lastName = lastName;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email  = email;}

    
    public Address getAddress(){return address;}
    public void setAddress(Address address){
        this.address = address;
        if (address != null){
        address.setEmployee(this);
        }
    }
}
