package com.example.restaurant_management_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restaurant_management_system.model.OrderLine;
import com.example.restaurant_management_system.service.OrderLineService;

@RestController
@RequestMapping("/api/order-lines")
public class OrderLineController {

    @Autowired
    private OrderLineService orderLineService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody OrderLine orderLine,
                                       @RequestParam("reservationId") UUID reservationId) {
        String message = orderLineService.save(orderLine, reservationId);
        if (message.equals("Order line saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else if (message.equals("The reservation with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderLine>> getAll() {
        return new ResponseEntity<>(orderLineService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/guest/{phone}")
    public ResponseEntity<List<OrderLine>> getByGuestPhone(@PathVariable("phone") String phone) {
        return new ResponseEntity<>(orderLineService.getByGuestPhone(phone), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        String message = orderLineService.delete(id);
        if (message.equals("Order line deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}