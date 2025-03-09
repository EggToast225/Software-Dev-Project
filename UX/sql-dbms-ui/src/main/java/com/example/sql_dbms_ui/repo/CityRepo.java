package com.example.sql_dbms_ui.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sql_dbms_ui.Models.City;

public interface CityRepo extends JpaRepository<City, Long>{

}
