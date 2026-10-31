package com.example.restaurant_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.model.Guest;

@Repository 
public interface GuestRepository extends JpaRepository<Guest, UUID> {
    
    List<Guest> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
