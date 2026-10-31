package com.example.restaurant_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.model.Guest;
import com.example.restaurant_management_system.repository.GuestRepository;

@Service 
public class GuestService {
    

    @Autowired 
    private GuestRepository guestRepository;

    public String save(Guest guest) {
        guestRepository.save(guest);
        return "Guest saved successfully";
    }

    public List<Guest> getAll() {
        return guestRepository.findAll();
    }

    public Optional<Guest> getById(UUID id) {
        return guestRepository.findById(id);
    }

    public Optional<Guest> update(UUID id, Guest guest)  {
        if (guestRepository.existsById(id)){
            guest.setId(id);
            return Optional.of(guestRepository.save(guest));
          }

          return Optional.empty();
    }

    public String delete(UUID id) {
        if (guestRepository.existsById(id)){
            guestRepository.deleteById(id);
            return "Guest deleted successfully";
        }
        return "The Guest with that id does not exist";
    }

    public List<Guest> getByLastName(String lastName) {
        return guestRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
}

