package com.example.restaurant_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.model.Reservation;
import com.example.restaurant_management_system.repository.ReservationRepository;

@Service 
public class ReservationService {
    
    @Autowired 
    private ReservationRepository reservationRepository;

    public String save(Reservation reservation) {
        reservationRepository.save(reservation);
        return "Reservation saved successfully";
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getById(UUID id) {
        return reservationRepository.findById(id);
    }

    public Optional<Reservation> update(UUID id, Reservation reservation){
        if (reservationRepository.existsById(id)){
            reservation.setId(id);
            return Optional.of(reservationRepository.save(reservation));
        }
        return Optional.empty();
    }

    public String delete(UUID id ) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return "Reservation deleted successfully";

        }
        return "The reservation with that id does not exist";
    }

    
}
