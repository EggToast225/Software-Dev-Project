package com.example.sql_dbms_ui.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


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

    @Column (unique = true)
    private String email;

    @Column
    private long jobTitleID; // This is a long because it's a primary key for EmployeeJobTitle Entity

    /*
    I'm taking a break from implementing this because the json query goes through, but you may need to make a repo for EACH entity/table and 
    then do some mapping that i'm not willing to do right now. Going to focus on html stuff

    ManyToOne // Multiple Employees can have same jobTitleID
    @JoinColumn(name = "jobTitleID")
    private EmployeeJobTitle employeeJobTitle; // make a private class that joins to jobTitleID
    */

    //Methods for getters and setters
    public long getEmpID(){return EmpID;}
    public void setEmpID(long EmpID){this.EmpID = EmpID;}

    public String getFirstName(){ return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getLastName(){return lastName;}
    public void setLastName(String lastName){this.lastName = lastName;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email  = email;}

    public long getJobTitleID(){return jobTitleID;} // return the employJobTitleID
    public void setJobTitleID(long jobTitleID){this.jobTitleID = jobTitleID;} // set private class as new private class



}
