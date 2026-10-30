package com.example.restaurant_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.model.Station;
import com.example.restaurant_management_system.repository.StationRepository;

@Service 
public class StationService {
    

    @Autowired 
    private StationRepository stationRepository;

    public String save(Station station) {
        stationRepository.save(station);
        return "Station saved successfully";
    }

    public List<Station> getAll() {
        return stationRepository.findAll();
    }

    public Optional<Station> update(UUID id) {
        return stationRepository.findById(id);
    }

    public Optional<Station> update(UUID id, Station station)  {
        if (stationRepository.existsById(id)){
            station.setId(id);
            return Optional.of(stationRepository.save(station));
          }

          return Optional.empty();
    }

    public String delete(UUID id) {
        if (stationRepository.existsById(id)){
            stationRepository.deleteById(id);
            return "Station deleted successfully";
        }
        return "The station with that id does not exist";
    }

    public Optional<Station> getById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }
}
