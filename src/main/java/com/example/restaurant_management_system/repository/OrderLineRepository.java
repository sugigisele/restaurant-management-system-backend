package com.example.restaurant_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.model.OrderLine;
import com.example.restaurant_management_system.model.Reservation;

@Repository 
public interface OrderLineRepository extends JpaRepository<OrderLine, UUID>
{
    @Query ("SELECT o FROM OrderLine o WHERE o.reservation.guest.phone = :phone ORDER BY o.orderedDate DESC")
    List<OrderLine> findByGuestPhone(@Param("phone") String phone);
    boolean existsByReservation(Reservation reservation);
}