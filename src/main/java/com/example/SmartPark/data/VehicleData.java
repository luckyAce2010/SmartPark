package com.example.SmartPark.data;

import com.example.SmartPark.pojo.Vehicle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//This will act as database for Vehicle
@Component
public class VehicleData {
    private static final List<Vehicle> vehicleList = new ArrayList<>();

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void registerVehicle(Vehicle vehicle){
        vehicleList.add(vehicle);
    }

    public boolean licensePlateExists(String requestId){

        for(Vehicle vehicle : getVehicleList()) {
            if(vehicle.getLicensePlate().equals(requestId)){return true;}
        }

        return false;

    }

    public Vehicle getVehicle(String licensePlate) {
        for(Vehicle vehicle : getVehicleList()) {
            if(vehicle.getLicensePlate().equals(licensePlate)){
                return vehicle;
            }
        }

        return null;
    }
}
