package com.example.restaurant_management_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.model.OrderLine;
import com.example.restaurant_management_system.model.Reservation;
import com.example.restaurant_management_system.model.ReservationStatus;
import com.example.restaurant_management_system.repository.OrderLineRepository;
import com.example.restaurant_management_system.repository.ReservationRepository;

@Service 
public class OrderLineService {
    
    @Autowired 
    private OrderLineRepository orderLineRepository;

    @Autowired ReservationRepository reservationRepository;

    public String save(OrderLine orderline, UUID reservationId) {
        Optional<Reservation> found = reservationRepository.findById(reservationId);
        if (found.isEmpty()){
            return "The reservation with that id does not exist";
        }

        Reservation reservation = found.get();
        if (reservation.getStatus() != ReservationStatus.SEATED) {
            return "Order lines are only for SEATED reservations";
        }
        orderline.setOrderedDate(LocalDate.now());
        orderline.setReservation(reservation);
        orderLineRepository.save(orderline);
        
        return "Order line saved successfully";
    }

    public List<OrderLine> getAll(){
        return orderLineRepository.findAll();
    }

    public List<OrderLine> getByGuestPhone(String phone) {
        return orderLineRepository.findByGuestPhone(phone);
    }

    public String delete(UUID id) {
        if (orderLineRepository.existsById(id)) {
            orderLineRepository.deleteById(id);
            return "Order line deleted successfully";
        }
        return "The order line with that id does not exist";
    }
}
