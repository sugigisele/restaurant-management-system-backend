package com.example.restaurant_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.model.Waiter;

@Repository 
public interface WaiterRepository  extends JpaRepository<Waiter, UUID> {

@Query ("SELECT DISTINCT w FROM Waiter w JOIN w.stations s WHERE LOWER(s.name) = LOWER(:name)")
List<Waiter> findWaitersByStationName(@Param("name") String name);
    }

