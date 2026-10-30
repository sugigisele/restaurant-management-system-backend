package com.example.restaurant_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.model.DiningTable;
import com.example.restaurant_management_system.repository.DiningTableRepository;

@Service 
public class DiningTableService {
    

    @Autowired 
    private DiningTableRepository diningTableRepository;

    public String save(DiningTable diningTable) {
        diningTableRepository.save(diningTable); 
            return "Dining table saved successfully";
    }

    public List<DiningTable> getAll() {
        return diningTableRepository.findAll();
    }

    public Optional<DiningTable> getById(UUID id) {
        return diningTableRepository.findById(id);
    }

    public Optional<DiningTable> update(UUID id, DiningTable diningTable) {
        if (diningTableRepository.existsById(id)){
            diningTable.setId(id);
            return Optional.of(diningTableRepository.save(diningTable));
        }
        return Optional.empty();

    }

    public String delete(UUID id) {
        if (diningTableRepository.existsById(id)) {
            diningTableRepository.deleteById(id);
            return "Dining table deleted successfully";

        }
        return "The dining table with that id does not exist";
    }
}
