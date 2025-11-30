package com.example.SmartPark.constants;

public class ParkingLotConstant {

    public static final String PARKING_CREATED = "Successfully Registered Parking Lot";
    public static final String PARKING_FULL = "Parking Lot is already full";

    public static String PARKING_ID_USED(String id) {
        return "Parking Lot ID: '" + id + "' is already in use";
    }
    public static String PARKING_ID_NOT_EXISTS(String id){
        return "Parking Lot ID: '" + id + "' doesn't exists";
    }
}
