package com.example.SmartPark.service.interfaces;

import com.example.SmartPark.dto.request.RegisterParkingLotRequest;
import com.example.SmartPark.dto.response.Response;

public interface ParkingLotService {

    Response<Void> registerParkingLot(RegisterParkingLotRequest createParkingLotReq);

    Response<Void> checkIn(String lotId, String licensePlate);
}
