package com.example.SmartPark.controller;

import com.example.SmartPark.dto.request.CheckInRequest;
import com.example.SmartPark.dto.request.RegisterParkingLotRequest;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.service.interfaces.ParkingLotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parking-lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    //Register parking lot
    @PostMapping
    public Response<Void> registerParkingLot(@Valid @RequestBody RegisterParkingLotRequest createParkingLotReq){
        return parkingLotService.registerParkingLot(createParkingLotReq);
    }

    //Check in into parking lot
    @PostMapping("/check-in")
    public Response<Void> checkInVehicle(@RequestBody @Valid CheckInRequest checkInRequest) {
        return parkingLotService.checkIn(checkInRequest.getLotId(), checkInRequest.getLicensePlate());
    }



}
