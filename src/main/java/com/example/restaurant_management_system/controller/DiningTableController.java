package com.example.restaurant_management_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restaurant_management_system.model.DiningTable;
import com.example.restaurant_management_system.service.DiningTableService;

@RestController 
@RequestMapping ("/api/dining-tables")

public class DiningTableController {

    @Autowired 
    private DiningTableService diningTableService;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody  DiningTable diningTable) {
        String message = diningTableService.save(diningTable);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping ("/all")
    public ResponseEntity<List<DiningTable>> getAll() {
        return new ResponseEntity<>(diningTableService.getAll(),HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getById(@PathVariable  UUID id){
    Optional<DiningTable> diningTable = diningTableService.getById(id);
    if (diningTable.isPresent()) {
        return new ResponseEntity<>(diningTable.get(), HttpStatus.OK);
        
    } else {
        return new ResponseEntity<>("The diningTable with that id does not exist", HttpStatus.NOT_FOUND);
    }
}

    @GetMapping ("/free")
    public ResponseEntity<List<DiningTable>> getFree() {
        return new ResponseEntity<>(diningTableService.getFree(), HttpStatus.OK);
    }
    
    @PutMapping ("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable  UUID id, @RequestBody DiningTable diningTable) {
        Optional<DiningTable> update = diningTableService.update(id, diningTable);
        if(update.isPresent()) {
            return new ResponseEntity<>(update.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The diningTable with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = diningTableService.delete(id);
        if (message.equals("Dining Table deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


}
