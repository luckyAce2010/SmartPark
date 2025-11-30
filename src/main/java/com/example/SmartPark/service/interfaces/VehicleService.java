package com.example.SmartPark.service.interfaces;

import com.example.SmartPark.dto.request.RegisterVehicleRequest;
import com.example.SmartPark.dto.response.Response;

public interface VehicleService {

    Response<Void> registerVehicle(RegisterVehicleRequest registerVehicleReq);
}
