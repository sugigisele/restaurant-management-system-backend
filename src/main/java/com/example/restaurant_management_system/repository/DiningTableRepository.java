package com.example.restaurant_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.model.DiningTable;

@Repository 
public interface DiningTableRepository extends JpaRepository<DiningTable, UUID>{
    
}
