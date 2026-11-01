package com.example.restaurant_management_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.model.Reservation;
import com.example.restaurant_management_system.model.ReservationStatus;
import com.example.restaurant_management_system.model.Waiter;

@Repository 
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByStatusOrderByReservationDateAsc(ReservationStatus status);
    List<Reservation> findByReservationDateBetweenOrderByReservationDateAsc(LocalDate start, LocalDate end);
    boolean existsByWaiterAndReservationDateAndStatusNot(Waiter waiter, LocalDate reservationDate, ReservationStatus status);

    @Query("SELECT r.status, COUNT(r) FROM Reservation r GROUP BY r.status")
    List<Object[]> countByStatus();
}