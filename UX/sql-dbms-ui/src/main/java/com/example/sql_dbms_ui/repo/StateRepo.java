package com.example.sql_dbms_ui.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sql_dbms_ui.Models.State;


public interface StateRepo extends JpaRepository<State, Long> {
    Optional<State> findByStateName(String stateName);
}
