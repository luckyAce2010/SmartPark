package com.example.SmartPark.constants;

public class VehicleConstant {

    public static final String TYPE_CAR = "Car";
    public static final String TYPE_MOTORCYCLE = "Motorcycle";
    public static final String TYPE_TRUCK = "Truck";


    public static final String VEHICLE_REGISTER_SUCCESS = "Successfully Registered Parking Lot";
    public static String VEHICLE_LICENSE_PLATE_USED(String id) {
        return "License Plate: '" + id + "' is already in use";
    }

}
