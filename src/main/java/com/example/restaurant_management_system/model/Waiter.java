package com.example.restaurant_management_system.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity 
@Table(name = "waiter")

public class Waiter {

@Id
@GeneratedValue (strategy = GenerationType.UUID)
private UUID id;


private String firstName;
private String lastName;
private String phone;

@ManyToOne 
@JoinColumn (name = "dining_table_id")
private DiningTable diningTable;


@ManyToMany 
@JoinTable (name = "waiter_station", joinColumns = @JoinColumn (name = "waiter_id"), inverseJoinColumns = @JoinColumn (name = "station_id"))


private List<Station> stations;

public UUID getId(){
    return id;
}

public void setId(UUID id) {
    this.id = id;
}

public String getFirstName( ){
    return firstName;
}

public void setFirstName( String firstName){
    this.firstName = firstName;
}

public String getLastName() {
    return lastName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;

}

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

public DiningTable getDiningTable() {
    return diningTable;
}
public void setDiningTable(DiningTable diningTable) {
    this.diningTable = diningTable;
}

public List<Station> getStations(){
    return stations;
}

public void setStations(List<Station> stations) {
    this.stations = stations;
}


    
}
