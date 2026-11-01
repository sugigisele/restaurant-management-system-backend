package com.example.restaurant_management_system.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restaurant_management_system.model.Reservation;
import com.example.restaurant_management_system.model.ReservationStatus;
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

    @GetMapping ("/by-status")
    public ResponseEntity<List<Reservation>> getByStatus(@RequestParam ReservationStatus status) {
        return new ResponseEntity<>(reservationService.getByStatus(status), HttpStatus.OK);
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(@PathVariable("id") UUID id,
                                           @RequestParam("status") ReservationStatus status) {
    String message = reservationService.changeStatus(id, status);
    if (message.equals("Reservation status updated successfully")) {
        return new ResponseEntity<>(message, HttpStatus.OK);
    } else if (message.equals("The reservation with that id does not exist")) {
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    } else {
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}

    @GetMapping ("/between")
    public ResponseEntity<List<Reservation>> getBetween(@RequestParam  String start, @RequestParam  String end){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(reservationService.getBetween(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        return new ResponseEntity<>(reservationService.getStatsByStatus(), HttpStatus.OK);
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

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam("waiterId") UUID waiterId,@RequestParam("date") String date) {
        LocalDate day = LocalDate.parse(date);
        String message = reservationService.cancelDay(waiterId, day);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

        
    @GetMapping("/page")
    public ResponseEntity<Page<Reservation>> getPage(  @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size, @RequestParam(value = "sort", defaultValue = "reservationDate,asc") String sort) {

         String[] parts = sort.split(",");
         String field = parts[0];

        Sort.Direction direction = Sort.Direction.ASC;
        if (parts.length > 1) {
            if (parts[1].equalsIgnoreCase("desc")) {
                direction = Sort.Direction.DESC;
            }
        }
          Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return new ResponseEntity<>(reservationService.getPage(pageable), HttpStatus.OK);
    }

}
