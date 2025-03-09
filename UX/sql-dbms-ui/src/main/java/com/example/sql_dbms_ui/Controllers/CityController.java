package com.example.sql_dbms_ui.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sql_dbms_ui.Models.City;
import com.example.sql_dbms_ui.repo.CityRepo;


@RestController
@RequestMapping(value = "/City")
public class CityController {

    // This handles the sql injection
    @Autowired
    private CityRepo cityRepo;

    // Creates new city on city Database
    // Gets a JSON, that JSON is in city variable, cityRepo uses setters create new city.
    @PostMapping (value = "/save")
    public ResponseEntity<String> saveCity(@RequestBody City city){
        cityRepo.save(city);
        return ResponseEntity.ok("saved city");
    }

    // Gets the city table
    @GetMapping (value = "/all")
    public List<City> getCities(){
        return cityRepo.findAll();
    }

    // Updates City name
    @PutMapping (value = "/update/{cityID}")
    public ResponseEntity<String> updateCity(@PathVariable ("cityID") long cityID, @RequestBody City city){
        City updatedCity = cityRepo.findById(cityID).get();
        updatedCity.setCityName(city.getCityName());

        return ResponseEntity.ok("updated city");
    }

    // Handles HTTP Delete request
    @DeleteMapping(value = "/delete/{cityID}") 
    public String deleteEmployee(@PathVariable("cityID") long cityID, @RequestBody City city){
        City deleteUser = cityRepo.findById(cityID).get();
        cityRepo.delete(deleteUser);
        return "deleted user with id: " + cityID;
    }
}
