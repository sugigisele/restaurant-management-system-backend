package com.example.restaurant_management_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.model.Guest;
import com.example.restaurant_management_system.model.Reservation;
import com.example.restaurant_management_system.model.ReservationStatus;
import com.example.restaurant_management_system.model.Waiter;
import com.example.restaurant_management_system.repository.GuestRepository;
import com.example.restaurant_management_system.repository.OrderLineRepository;
import com.example.restaurant_management_system.repository.ReservationRepository;
import com.example.restaurant_management_system.repository.WaiterRepository;

@Service 
public class ReservationService {
    
    @Autowired 
    private ReservationRepository reservationRepository;

    @Autowired 
    private GuestRepository guestRepository;

    @Autowired 
    private WaiterRepository waiterRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;


    public void attachRelations(Reservation reservation) {

        if (reservation.getGuest()  != null){
            if (reservation.getGuest().getId() != null) {
                Optional<Guest> guest = guestRepository.findById(reservation.getGuest().getId());
                if(guest.isPresent()) {
                    reservation.setGuest(guest.get());
                }

                if (reservation.getWaiter() != null) {
                    if(reservation.getWaiter().getId() != null) {
                            Optional<Waiter> waiter = waiterRepository.findById(reservation.getWaiter().getId());
                            if (waiter.isPresent()) {
                                reservation.setWaiter(waiter.get());
                            }
                        }
                    }
                }
            }
        
        }
        

    public String save(Reservation reservation) {
        if (reservation.getReservationDate() != null){
            if (reservation.getReservationDate().isBefore(LocalDate.now())) {
                 return "Reservation date cannot be in the past";
            }
        }

        attachRelations(reservation);

        if (reservation.getWaiter()!= null) {
            boolean busy = reservationRepository.existsByWaiterAndReservationDateAndStatusNot( reservation.getWaiter(), reservation.getReservationDate(), ReservationStatus.CANCELLED);
            if (busy) {
                return "Waiter already has a reservation on that date";
            }
        }

        reservation.setStatus(ReservationStatus.BOOKED);
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
        Optional<Reservation> existing = reservationRepository.findById(id);
        if (existing.isPresent()){
            reservation.setId(id);
            reservation.setStatus(existing.get().getStatus());
            attachRelations(reservation);
            return Optional.of(reservationRepository.save(reservation));
        }
        return Optional.empty();
    }
    public String delete(UUID id) {
    Optional<Reservation> found = reservationRepository.findById(id);
    if (found.isEmpty()) {
        return "The reservation with that id does not exist";
    }

    if (orderLineRepository.existsByReservation(found.get())) {
        return "That reservation has order lines, delete them first";
    }

    reservationRepository.deleteById(id);
    return "Reservation deleted successfully";
}

    public List<Reservation> getByStatus(ReservationStatus status) {
        return reservationRepository.findByStatusOrderByReservationDateAsc(status);
    }

    public List<Reservation> getBetween(LocalDate start, LocalDate end) {
        return reservationRepository.findByReservationDateBetweenOrderByReservationDateAsc(start, end);
    }

    public String changeStatus(UUID id, ReservationStatus newStatus){
        Optional<Reservation> found = reservationRepository. findById(id);
        if (found.isEmpty()) {
            return "The reservation with that id does not exist";
        }

        Reservation reservation = found.get();
        ReservationStatus current = reservation.getStatus();

        boolean allowed = false;

        if (current == ReservationStatus.BOOKED) {
            if (newStatus == ReservationStatus.ARRIVED) {
                allowed = true;
            }
            if (newStatus == ReservationStatus.CANCELLED) {
                allowed  = true;
            }
        }

        if (current == ReservationStatus.ARRIVED) {
            if (newStatus == ReservationStatus.SEATED) {
                allowed = true;
            }

        }

        if (allowed == false) {
            return "Cannot change status from " + current + "to" + newStatus;
        }

        reservation.setStatus(newStatus);
        reservationRepository.save(reservation);
        return "Reservation status updated successfully";
    }
    
    public List<Object[]> getStatsByStatus() {
        return reservationRepository.countByStatus();
    }
}
