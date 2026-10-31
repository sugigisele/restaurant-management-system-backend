package com.example.restaurant_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.model.DiningTable;

@Repository 
public interface DiningTableRepository extends JpaRepository<DiningTable, UUID>{
   
    @Query ("SELECT d FROM DiningTable d WHERE NOT EXISTS (SELECT w FROM Waiter w WHERE w.diningTable.id = d.id) ORDER BY d.tableNumber")
    List<DiningTable> findFreeDiningTables();
}
