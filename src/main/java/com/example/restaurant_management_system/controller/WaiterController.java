package com.example.restaurant_management_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restaurant_management_system.model.Waiter;
import com.example.restaurant_management_system.service.WaiterService;

@RestController 
@RequestMapping ("/api/waiters")

public class WaiterController {

    @Autowired 
    private WaiterService waiterService;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody  Waiter waiter) {
        String message = waiterService.save(waiter);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping ("/all")
    public ResponseEntity<List<Waiter>> getAll() {
        return new ResponseEntity<>(waiterService.getAll(),HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getById(@PathVariable  UUID id){
    Optional<Waiter> waiter = waiterService.getById(id);
    if (waiter.isPresent()) {
        return new ResponseEntity<>(waiter.get(), HttpStatus.OK);
        
    } else {
        return new ResponseEntity<>("The waiter with that id does not exist", HttpStatus.NOT_FOUND);
    }
}
    @PutMapping ("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable  UUID id, @RequestBody Waiter waiter) {
        Optional<Waiter> update = waiterService.update(id, waiter);
        if(update.isPresent()) {
            return new ResponseEntity<>(update.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The waiter with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = waiterService.delete(id);
        if (message.equals("Waiter  deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


}
