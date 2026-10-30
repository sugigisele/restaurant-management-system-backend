package com.example.restaurant_management_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restaurant_management_system.model.Reservation;
import com.example.restaurant_management_system.service.ReservationService;

@RestController 
@RequestMapping ("/api/reservations")

public class ReservationController {

    @Autowired 
    private ReservationService reservationService;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody  Reservation reservation) {
        String message = reservationService.save(reservation);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAll() {
        return new ResponseEntity<>(reservationService.getAll(), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getById(@PathVariable  UUID id){
    Optional<Reservation> reservation = reservationService.getById(id);
    if (reservation.isPresent()) {
        return new ResponseEntity<>(reservation.get(), HttpStatus.OK);
        
    } else {
        return new ResponseEntity<>("The Reservation with that id does not exist", HttpStatus.NOT_FOUND);
    }
}
    @PutMapping ("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable  UUID id, @RequestBody Reservation Reservation) {
        Optional<Reservation> update = reservationService.update(id, Reservation);
        if(update.isPresent()) {
            return new ResponseEntity<>(update.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The reservation with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = reservationService.delete(id);
        if (message.equals("Reservation  deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


}
