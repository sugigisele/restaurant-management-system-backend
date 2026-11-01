package com.example.restaurant_management_system.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
@Entity 
@Table(name = "order_line")
public class OrderLine {


    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)

    private UUID id;

    private String dishName;
    private String specialRequest;
    private int quantity;
    private LocalDate orderedDate;

    @ManyToOne 
    @JoinColumn (name = "reservation_id")
    private Reservation reservation;
    
    

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(LocalDate orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
