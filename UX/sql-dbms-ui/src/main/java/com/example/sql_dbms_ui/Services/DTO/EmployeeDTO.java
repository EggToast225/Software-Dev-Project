package com.example.sql_dbms_ui.Services.DTO;

import java.sql.Date;

public class EmployeeDTO {
    public String firstName;
    public String lastName;
    public String email;
    public Date hireDate;
    public double salary;
    public String ssn;
    public String gender;
    public String identifiedRace;
    public Date dob;
    public String phone;

    public AddressDTO address;

    public static class AddressDTO {
        public String street;
        public String zip;
        public String cityName;
        public String stateName;
    }
}