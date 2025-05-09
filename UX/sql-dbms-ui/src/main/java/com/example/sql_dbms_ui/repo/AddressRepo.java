package com.example.sql_dbms_ui.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sql_dbms_ui.Models.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

}
