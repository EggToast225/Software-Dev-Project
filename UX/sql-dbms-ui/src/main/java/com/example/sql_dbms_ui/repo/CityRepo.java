package com.example.sql_dbms_ui.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sql_dbms_ui.Models.City;

public interface CityRepo extends JpaRepository<City, Long>{
    Optional<City> findByCityName(String cityName);
}
