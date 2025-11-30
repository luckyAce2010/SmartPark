package com.example.SmartPark.service.impl;

import com.example.SmartPark.data.ParkingLotData;
import com.example.SmartPark.data.VehicleData;
import com.example.SmartPark.dto.request.RegisterParkingLotRequest;
import com.example.SmartPark.dto.response.ParkingLotAvailabilityResponse;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.dto.response.VehicleResponse;
import com.example.SmartPark.pojo.ParkingLot;
import com.example.SmartPark.pojo.Vehicle;
import com.example.SmartPark.service.interfaces.ParkingLotService;
import com.example.SmartPark.utils.ResponseUtil;
import com.example.SmartPark.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        int value = vehicleAlreadyCheckIn(vehicle);
        if(value != 3){
            return value == 1 ?
                    ResponseUtil.error(VEHICLE_LICENSE_PLATE_NOT_EXISTS(licensePlate), BAD_REQUEST_CODE) :
                    ResponseUtil.error(VEHICLE_ALREADY_CHECKED_IN, BAD_REQUEST_CODE);
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
        vehicle.setCurrentParkingLot(lotId);
        parkingLot.getVehicles().add(vehicle);
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() + 1);

        return ResponseUtil.success(VEHICLE_PARKED_SUCCESS, SUCCESS_CODE);
    }

    @Override
    public Response<Void> checkOut(String licensePlate) {

        //Trim whitespaces and unnecessary whitespaces
        licensePlate = StringUtils.removeUnnecessaryWhiteSpaces(licensePlate);

        //Validate Vehicle ID if exists
        //Check if Vehicle already checked in
        Vehicle vehicle = vehicleData.getVehicle(licensePlate);
        int value = vehicleAlreadyCheckIn(vehicle);
        if(value != 2){
            return value == 1 ?
                    ResponseUtil.error(VEHICLE_LICENSE_PLATE_NOT_EXISTS(licensePlate), BAD_REQUEST_CODE) :
                    ResponseUtil.error(VEHICLE_NOT_CHECKED_IN, BAD_REQUEST_CODE);
        }

        //Get Parking Lot of Vehicle
        ParkingLot parkingLot = parkingLotData.getParkingLot(vehicle.getCurrentParkingLot());

        //Check out vehicle
        vehicle.setCurrentParkingLot(null);
        vehicle.setCheckedIn(false);
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() - 1);
        parkingLot.getVehicles().remove(vehicle);

        return ResponseUtil.success(VEHICLE_CHECK_OUT_SUCCESS, SUCCESS_CODE);
    }

    @Override
    public Response<ParkingLotAvailabilityResponse> checkParkingLotAvailability(String lotId) {

        //Trim whitespaces and unnecessary whitespaces
        lotId = StringUtils.removeUnnecessaryWhiteSpaces(lotId);

        //Validate Parking lot ID if exists
        ParkingLot parkingLot = parkingLotData.getParkingLot(lotId);
        if(parkingLot == null){
            return ResponseUtil.error(null, PARKING_ID_NOT_EXISTS(lotId), BAD_REQUEST_CODE);
        }

        ParkingLotAvailabilityResponse parkingAvailabilityResp =
                ParkingLotAvailabilityResponse.builder()
                        .lotId(lotId)
                        .available(parkingLot.getCapacity() - parkingLot.getOccupiedSpaces())
                        .occupied(parkingLot.getOccupiedSpaces())
                        .build();

        return ResponseUtil.success(parkingAvailabilityResp, GET_PARKING_AVAILABITY_SUCCESS, SUCCESS_CODE);
    }

    @Override
    public Response<List<VehicleResponse>> getVehicles(String lotId) {

        //Trim whitespaces and unnecessary whitespaces
        lotId = StringUtils.removeUnnecessaryWhiteSpaces(lotId);

        //Validate Parking lot ID if exists
        ParkingLot parkingLot = parkingLotData.getParkingLot(lotId);
        if(parkingLot == null){
            return ResponseUtil.error(null, PARKING_ID_NOT_EXISTS(lotId), BAD_REQUEST_CODE);
        }

        //Build the response
        List<VehicleResponse> vehicleResponseList = new ArrayList<>();
        for(Vehicle vehicle : parkingLot.getVehicles()){
            VehicleResponse vehicleResponse = VehicleResponse.builder()
                    .licensePlate(vehicle.getLicensePlate())
                    .ownerName(vehicle.getOwnerName())
                    .vehicleType(vehicle.getVehicleType())
                    .build();

            vehicleResponseList.add(vehicleResponse);
        }

        return ResponseUtil.success(vehicleResponseList, GET_VEHICLES_IN_PARKING_SUCCESS, SUCCESS_CODE);
    }


    //Helper Methods
    private int vehicleAlreadyCheckIn(Vehicle vehicle){
        if(vehicle == null){
            return 1;
        }
        if(vehicle.getCheckedIn()){
            return 2;
        }

        return 3;
    }


}
