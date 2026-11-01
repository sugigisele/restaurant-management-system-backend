package com.example.restaurant_management_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Modifying(clearAutomatically = true)
    
    @Query("UPDATE Reservation r SET r.status = :newStatus WHERE r.waiter.id = :waiterId AND r.reservationDate = :date AND r.status <> :keepStatus")

    int cancelDay(@Param("waiterId") UUID waiterId, @Param("date") LocalDate date, @Param("newStatus") ReservationStatus newStatus, @Param("keepStatus") ReservationStatus keepStatus);

    long countByReservationDate(LocalDate reservationDate);

    long countByStatusIs(ReservationStatus status);              
}