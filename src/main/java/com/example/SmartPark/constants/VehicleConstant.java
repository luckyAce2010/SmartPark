package com.example.SmartPark.constants;

public class VehicleConstant {

    public static final String TYPE_CAR = "Car";
    public static final String TYPE_MOTORCYCLE = "Motorcycle";
    public static final String TYPE_TRUCK = "Truck";


    public static final String VEHICLE_REGISTER_SUCCESS = "Successfully Registered Vehicle";
    public static final String VEHICLE_ALREADY_CHECKED_IN = "Vehicle is already checked in";
    public static final String VEHICLE_PARKED_SUCCESS = "Successfully Checked in Vehicle";
    public static String VEHICLE_LICENSE_PLATE_USED(String id) {
        return "License Plate: '" + id + "' is already in use";
    }
    public static String VEHICLE_LICENSE_PLATE_NOT_EXISTS(String id) {
        return "License Plate: '" + id + "' doesn't exists";
    }

}
