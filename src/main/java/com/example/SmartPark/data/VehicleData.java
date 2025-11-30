package com.example.SmartPark.data;

import com.example.SmartPark.pojo.Vehicle;

import java.util.ArrayList;
import java.util.List;

//This will act as database for Vehicle
public class VehicleData {
    private static final List<Vehicle> vehicleList = new ArrayList<>();

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void registerVehicle(Vehicle vehicle){
        vehicleList.add(vehicle);
    }
}
