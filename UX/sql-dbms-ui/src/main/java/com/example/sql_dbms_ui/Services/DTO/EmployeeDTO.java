package com.example.sql_dbms_ui.Services.DTO;

public class EmployeeDTO {
    public String firstName;
    public String lastName;
    public String email;
    public String hireDate;
    public double salary;
    public String ssn;
    public String gender;
    public String identifiedRace;
    public String dob;
    public String phone;

    public AddressDTO address;

    public static class AddressDTO {
        public String street;
        public String zip;
        public String cityName;
        public String stateName;
    }
}