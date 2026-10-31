package com.example.restaurant_management_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restaurant_management_system.model.Guest;
import com.example.restaurant_management_system.service.GuestService;

@RestController 
@RequestMapping ("/api/guests")

public class GuestController {

    @Autowired 
    private GuestService guestService;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody  Guest guest) {
        String message = guestService.save(guest);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping ("/all")
    public ResponseEntity<List<Guest>> getAll() {
        return new ResponseEntity<>(guestService.getAll(),HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getById(@PathVariable  UUID id){
    Optional<Guest> Guest = guestService.getById(id);
    if (Guest.isPresent()) {
        return new ResponseEntity<>(Guest.get(), HttpStatus.OK);
        
    } else {
        return new ResponseEntity<>("The Guest with that id does not exist", HttpStatus.NOT_FOUND);
    }
}

    @GetMapping ("/by-last-name")
    public ResponseEntity<List<Guest>> getByLastName(@RequestParam ("lastName") String lastName) {
        return new ResponseEntity<>(guestService.getByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping ("/of-waiter/{waiterId}")
    public ResponseEntity<Object> getOfWaiter(@PathVariable("waiterId") UUID waiterId){
        Optional<List<Guest>> guest = guestService.getOfWaiter(waiterId);
        if (guest.isPresent()) {
            return new ResponseEntity<>(guest.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The waiter with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable  UUID id, @RequestBody Guest Guest) {
        Optional<Guest> update = guestService.update(id, Guest);
        if(update.isPresent()) {
            return new ResponseEntity<>(update.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The guest with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = guestService.delete(id);
        if (message.equals("Guest  deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


}
