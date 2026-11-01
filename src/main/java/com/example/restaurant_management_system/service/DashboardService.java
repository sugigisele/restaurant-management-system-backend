package com.example.restaurant_management_system.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.model.ReservationStatus;
import com.example.restaurant_management_system.repository.*;

@Service
public class DashboardService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private WaiterRepository waiterRepository;

    @Autowired
    private DiningTableRepository diningTableRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    public Map<String, Long> getSummary() {
        
        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("guests", guestRepository.count());
        summary.put("waiters", waiterRepository.count());

        
        long free = diningTableRepository.findFreeDiningTables().size();
        summary.put("freeDiningTables", free);

        summary.put("reservationsToday", reservationRepository.countByReservationDate(LocalDate.now()));
        summary.put("booked", reservationRepository.countByStatusIs(ReservationStatus.BOOKED));
        summary.put("orderLines", orderLineRepository.count());

        return summary;
    }
}