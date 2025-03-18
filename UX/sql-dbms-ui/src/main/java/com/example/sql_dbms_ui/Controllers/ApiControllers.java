package com.example.sql_dbms_ui.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.sql_dbms_ui.Models.Employees;
import com.example.sql_dbms_ui.repo.EmployeesRepo;




@RestController
public class ApiControllers {

    @Autowired // Handles dependency injection
    private EmployeesRepo EmployeeRepo;

    @GetMapping(value = "/")
    public String getPage() {
        return "Welcome";
    }

    @GetMapping(value = "/Employees")
    public List<Employees> getEmployees(){
        return EmployeeRepo.findAll();
    }

    @PostMapping(value = "/save") //@PostMapping used because we want to save to database (posting), handles HTTP Post request
    public String saveEmployee(@RequestBody Employees employee) {
        EmployeeRepo.save(employee);  
        return "saved";
    }


    @PutMapping(value ="update/{empid}") //Updates the User by their primary key/id, handles HTTP Put request
    public String updateEmployee(@PathVariable("empid") long EmpID, @RequestBody Employees employee){
        Employees updatedEmployee = EmployeeRepo.findById(EmpID).get();
        updatedEmployee.setFirstName(employee.getFirstName());
        updatedEmployee.setLastName(employee.getLastName());
        //updatedEmployee.setJobTitleID(employee.getJobTitleID());

        updatedEmployee.setEmail(employee.getEmail());

        EmployeeRepo.save(updatedEmployee);
        return "updated";
    }

    @DeleteMapping(value = "/delete/{id}") // Handles HTTP Delete request
    public String deleteEmployee(@PathVariable("id") long EmpID, @RequestBody Employees employee){
        Employees deleteUser = EmployeeRepo.findById(EmpID).get();
        EmployeeRepo.delete(deleteUser);
        return "deleted user with id: " + EmpID;
    }
}
