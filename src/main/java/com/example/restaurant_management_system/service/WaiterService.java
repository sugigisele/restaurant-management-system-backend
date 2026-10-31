package com.example.restaurant_management_system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void attachRelations(Waiter waiter) {
        if (waiter.getDiningTable() != null) {
            if (waiter.getDiningTable().getId() != null) {
                Optional<DiningTable> table =
                        diningTableRepository.findById(waiter.getDiningTable().getId());
                if (table.isPresent()) {
                    waiter.setDiningTable(table.get());
                }
            }
        }
        if (waiter.getStations() != null) {
            List<Station> realStations = new ArrayList<>();
            for (Station s : waiter.getStations()) {
                if (s.getId() != null) {
                    Optional<Station> station = stationRepository.findById(s.getId());
                    if (station.isPresent()) {
                        realStations.add(station.get());
                    }
                }
            }
            waiter.setStations(realStations);
        }
    }

    public String save(Waiter waiter) {
        attachRelations(waiter);
        waiterRepository.save(waiter);
        return "Waiter saved successfully";
    }

    public List<Waiter> getAll() {
        return waiterRepository.findAll();
    }

    public Optional<Waiter> getById(UUID id) {
        return waiterRepository.findById(id);
    }

    public Optional<Waiter> update(UUID id, Waiter waiter) {
        if (waiterRepository.existsById(id)) {
            waiter.setId(id);
            attachRelations(waiter);
            return Optional.of(waiterRepository.save(waiter));
        }
        return Optional.empty();
    }

    public String delete(UUID id) {
        if (waiterRepository.existsById(id)) {
            waiterRepository.deleteById(id);
            return "Waiter deleted successfully";
        }
        return "The waiter with that id does not exist";
    }

    // Part C1
    public List<Waiter> getByStation(String name) {
        return waiterRepository.findWaitersByStationName(name);
    }

    // D3: add one station, keeping the ones already there
    @Transactional
    public String addStation(UUID waiterId, UUID stationId) {
        Optional<Waiter> waiterOpt = waiterRepository.findById(waiterId);
        if (waiterOpt.isEmpty()) {
            return "The waiter with that id does not exist";
        }

        Optional<Station> stationOpt = stationRepository.findById(stationId);
        if (stationOpt.isEmpty()) {
            return "That station does not exist";
        }

        Waiter waiter = waiterOpt.get();
        Station station = stationOpt.get();

        if (waiter.getStations() == null) {
            waiter.setStations(new ArrayList<>());
        }

        if (waiter.getStations().contains(station)) {
            return "The waiter already has that station";
        }

        waiter.getStations().add(station);
        waiterRepository.save(waiter);
        return "Station added to the waiter";
    }

    // D4: move a waiter to another dining table
    @Transactional
    public String changeDiningTable(UUID waiterId, int tableNumber) {
        Optional<Waiter> waiterOpt = waiterRepository.findById(waiterId);
        if (waiterOpt.isEmpty()) {
            return "The waiter with that id does not exist";
        }

        Optional<DiningTable> tableOpt = diningTableRepository.findByTableNumber(tableNumber);
        if (tableOpt.isEmpty()) {
            return "The dining table with that tableNumber does not exist";
        }

        Waiter waiter = waiterOpt.get();
        DiningTable newTable = tableOpt.get();

        if (waiter.getDiningTable() != null) {
            if (waiter.getDiningTable().getId().equals(newTable.getId())) {
                return "Dining table changed successfully";
            }
        }

        if (waiterRepository.existsByDiningTable(newTable)) {
            return "That dining table is taken by another waiter";
        }

        waiter.setDiningTable(newTable);
        waiterRepository.save(waiter);
        return "Dining table changed successfully";
    }
}