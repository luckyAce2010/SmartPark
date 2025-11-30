package com.example.SmartPark.data;

import com.example.SmartPark.pojo.ParkingLot;

import java.util.ArrayList;
import java.util.List;

//This will act as database for Parking Lot
public class ParkingLotData {

    private static final List<ParkingLot> parkingLotList = new ArrayList<>();

    public List<ParkingLot> getParkingLotList() {
        return parkingLotList;
    }

    public void registerParkingLot(ParkingLot parkingLot){
        parkingLotList.add(parkingLot);
    }

    public boolean parkingLotIdExists(String lotId){

        for(ParkingLot parkingLot : getParkingLotList()) {
            if(parkingLot.getLotId().equals(lotId)){return true;}
        }

        return false;

    }

    public ParkingLot getParkingLot(String lotId) {

        for(ParkingLot parkingLot : getParkingLotList()) {
            if(parkingLot.getLotId().equals(lotId)){
                return parkingLot;
            }
        }

        return null;
    }
}
