package com.example.restaurant_management_system.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "reservation")

public class Reservation {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate reservationDate;

    @Enumerated (EnumType.STRING)

    private ReservationStatus status;

    private int partySize;

    @ManyToOne 
    @JoinColumn(name = "waiter id")
    private Waiter waiter;

    @ManyToOne 
    @JoinColumn(name = "guest_id")
    private Guest guest;
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public LocalDate getReservationDate() {
        return reservationDate;
    }
    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;

    }
    public ReservationStatus getStatus() {
        return status;
    }
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public int getPartySize() {
        return partySize;
    }
    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }
    public Guest getGuest() {
        return guest;
    }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public Waiter getWaiter(){
        return waiter;
    }
    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

}
