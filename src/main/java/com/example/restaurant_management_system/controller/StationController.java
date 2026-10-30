package com.example.restaurant_management_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant_management_system.model.Station;
import com.example.restaurant_management_system.service.StationService;

@RestController 
@RequestMapping ("/api/stations")

public class StationController {
    @Autowired 
    private StationService stationService;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody  Station station) {
        return new ResponseEntity<>(stationService.save(station), HttpStatus.CREATED);
    }

    @GetMapping ("/all")
    public ResponseEntity<List<Station>> getAll() {
        return new ResponseEntity<>(stationService.getAll(),HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getById(@PathVariable  UUID id){
    Optional<Station> station = stationService.getById(id);
    if (station.isPresent()) {
        return new ResponseEntity<>(station.get(), HttpStatus.OK);
        
    } else {
        return new ResponseEntity<>("The station with that id does not exist", HttpStatus.NOT_FOUND);
    }
}
    @PutMapping ("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable  UUID id, @RequestBody Station station) {
        Optional<Station> update = stationService.update(id, station);
        if(update.isPresent()) {
            return new ResponseEntity<>(update.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The station with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = stationService.delete(id);
        if (message.equals("Station deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


}
