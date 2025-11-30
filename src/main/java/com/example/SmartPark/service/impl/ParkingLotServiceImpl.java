package com.example.SmartPark.service.impl;

import com.example.SmartPark.data.ParkingLotData;
import com.example.SmartPark.data.VehicleData;
import com.example.SmartPark.dto.request.RegisterParkingLotRequest;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.pojo.ParkingLot;
import com.example.SmartPark.pojo.Vehicle;
import com.example.SmartPark.service.interfaces.ParkingLotService;
import com.example.SmartPark.utils.ResponseUtil;
import com.example.SmartPark.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.example.SmartPark.constants.ParkingLotConstant.*;
import static com.example.SmartPark.constants.ResponseConstant.BAD_REQUEST_CODE;
import static com.example.SmartPark.constants.ResponseConstant.SUCCESS_CODE;
import static com.example.SmartPark.constants.VehicleConstant.*;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotData parkingLotData = new ParkingLotData();
    private final VehicleData vehicleData = new VehicleData();

    @Override
    public Response<Void> registerParkingLot(RegisterParkingLotRequest createParkingLotReq) {

        //Trim whitespaces and unnecessary whitespaces
        String idReq = StringUtils.removeUnnecessaryWhiteSpaces(createParkingLotReq.getLotId());

        //Check ID if already exists
        if(parkingLotData.parkingLotIdExists(idReq)){
            return ResponseUtil.error(PARKING_ID_USED(idReq), BAD_REQUEST_CODE);
        }

        ParkingLot parkingLot = ParkingLot.builder()
                .lotId(idReq)
                .capacity(createParkingLotReq.getCapacity())
                .location(createParkingLotReq.getLocation())
                .occupiedSpaces(0)
                .vehicles(new ArrayList<>())
                .build();

        parkingLotData.registerParkingLot(parkingLot);

        return ResponseUtil.success(PARKING_CREATED, SUCCESS_CODE);
    }

    @Override
    public Response<Void> checkIn(String lotId, String licensePlate) {

        //Trim whitespaces and unnecessary whitespaces
        lotId = StringUtils.removeUnnecessaryWhiteSpaces(lotId);
        licensePlate = StringUtils.removeUnnecessaryWhiteSpaces(licensePlate);


        //Validate Vehicle ID if exists
        //Check if Vehicle already checked in
        Vehicle vehicle = vehicleData.getVehicle(licensePlate);
        if(vehicle == null){
            return ResponseUtil.error(VEHICLE_LICENSE_PLATE_NOT_EXISTS(licensePlate), BAD_REQUEST_CODE);
        }
        if(vehicle.getCheckedIn()){
            return ResponseUtil.error(VEHICLE_ALREADY_CHECKED_IN, BAD_REQUEST_CODE);
        }

        //Check if Parking lot is full
        //Validate Parking lot ID if exists
        ParkingLot parkingLot = parkingLotData.getParkingLot(lotId);
        if(parkingLot == null){
            return ResponseUtil.error(PARKING_ID_NOT_EXISTS(lotId), BAD_REQUEST_CODE);
        }
        if(parkingLot.getCapacity() - parkingLot.getOccupiedSpaces()  == 0) {
            return ResponseUtil.error(PARKING_FULL, BAD_REQUEST_CODE);
        }

        //Check in vehicle if all conditions are met
        vehicle.setCheckedIn(true);
        parkingLot.getVehicles().add(vehicle);
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() + 1);

        return ResponseUtil.success(VEHICLE_PARKED_SUCCESS, SUCCESS_CODE);
    }


}
