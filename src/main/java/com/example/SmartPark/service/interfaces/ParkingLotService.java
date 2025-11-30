package com.example.SmartPark.service.interfaces;

import com.example.SmartPark.dto.request.RegisterParkingLotRequest;
import com.example.SmartPark.dto.response.ParkingLotAvailabilityResponse;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.dto.response.VehicleResponse;

import java.util.List;

public interface ParkingLotService {

    Response<Void> registerParkingLot(RegisterParkingLotRequest createParkingLotReq);

    Response<Void> checkIn(String lotId, String licensePlate);

    Response<Void> checkOut(String licensePlate);

    Response<ParkingLotAvailabilityResponse> checkParkingLotAvailability(String lotId);

    Response<List<VehicleResponse>> getVehicles(String lotId);
}
