package com.example.SmartPark.service.interfaces;

import com.example.SmartPark.dto.request.CreateParkingLotRequest;
import com.example.SmartPark.dto.response.Response;

public interface ParkingLotService {

    Response<Void> registerParkingLot(CreateParkingLotRequest createParkingLotReq);
}
