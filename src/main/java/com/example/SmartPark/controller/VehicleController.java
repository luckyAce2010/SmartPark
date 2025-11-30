package com.example.SmartPark.controller;

import com.example.SmartPark.dto.request.RegisterVehicleRequest;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.service.interfaces.VehicleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public Response<Void> registerVehicle(@Valid @RequestBody RegisterVehicleRequest registerVehicleReq){
        return vehicleService.registerVehicle(registerVehicleReq);
    }

}
