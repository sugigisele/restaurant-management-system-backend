package com.example.restaurant_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.model.Station;


@Repository 
public interface StationRepository extends JpaRepository<Station, UUID> {
    
     @Query ("SELECT s FROM Station s WHERE s.waiters IS EMPTY")
     List<Station> findUnusedStations();   
    }

