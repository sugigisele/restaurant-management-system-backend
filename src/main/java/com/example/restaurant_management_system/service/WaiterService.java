package com.example.restaurant_management_system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.model.DiningTable;
import com.example.restaurant_management_system.model.Station;
import com.example.restaurant_management_system.model.Waiter;
import com.example.restaurant_management_system.repository.DiningTableRepository;
import com.example.restaurant_management_system.repository.StationRepository;
import com.example.restaurant_management_system.repository.WaiterRepository;

@Service 
public class WaiterService {
    

    @Autowired 
    private WaiterRepository waiterRepository;

    @Autowired 
    private StationRepository stationRepository;

    @Autowired 
    private DiningTableRepository diningTableRepository;

    public void attachRelations(Waiter waiter) {

        if (waiter.getDiningTable()  != null){
            if (waiter.getDiningTable().getId() != null) {
                Optional<DiningTable> table = diningTableRepository.findById(waiter.getDiningTable().getId());
                if(table.isPresent()) {
                    waiter.setDiningTable(table.get());
                }

                if (waiter.getStations() != null) {
                    List<Station> realStation = new ArrayList<>();
                    for (Station s: waiter.getStations()) {
                        if (s.getId() != null) {
                            Optional<Station> station = stationRepository.findById(s.getId());
                            if (station.isPresent()) {
                                realStation.add(station.get());
                            }
                        }
                    }
                }
            }
        }
    }
    

    public String save(Waiter waiter) {
        waiterRepository.save(waiter);
        return "Waiter saved successfully";
    }

    public List<Waiter> getAll() {
        return waiterRepository.findAll();
    }
    public  List<Waiter> getByStation(String name) {
        return waiterRepository.findWaitersByStationName(name);
    }

    public Optional<Waiter> getById(UUID id) {
        return waiterRepository.findById(id);
    }

    public Optional<Waiter> update(UUID id, Waiter waiter)  {
        if (waiterRepository.existsById(id)){
            waiter.setId(id);
            return Optional.of(waiterRepository.save(waiter));
          }

          return Optional.empty();
    }

    public String delete(UUID id) {
        if (waiterRepository.existsById(id)){
            waiterRepository.deleteById(id);
            return "waiter deleted successfully";
        }
        return "The waiter with that id does not exist";
    }
}

