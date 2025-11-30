package com.example.SmartPark.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ParkingLot {

    String lotId;
    String location;
    Integer capacity;
    Integer occupiedSpaces;
    List<Vehicle> vehicles;

}
