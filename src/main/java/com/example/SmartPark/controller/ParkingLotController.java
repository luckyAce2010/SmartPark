package com.example.SmartPark.controller;

import com.example.SmartPark.dto.request.RegisterParkingLotRequest;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.service.interfaces.ParkingLotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
