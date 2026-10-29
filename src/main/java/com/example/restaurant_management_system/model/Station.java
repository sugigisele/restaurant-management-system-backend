package com.example.restaurant_management_system.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity 
@Table (name = "station")
public class Station {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    
    @ManyToMany (mappedBy = "stations")
    @JsonIgnore
    private List<Waiter> waiters;

    public UUID getId() {
        
        return id;
    }

    public void setId(UUID ID) {
        this.id = id;
    }

    public String getName () { 
        return name;
    }

    public void setName(String name) {
         this.name =  name;
         }

    public List <Waiter> getWaiters() { 
        return waiters;
    }

    public void setWaiters(List<Waiter> waiters) { 
        this.waiters = waiters;
    }

}
