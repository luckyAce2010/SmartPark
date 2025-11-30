package com.example.SmartPark.controller;

import com.example.SmartPark.dto.request.CheckInRequest;
import com.example.SmartPark.dto.request.CheckOutRequest;
import com.example.SmartPark.dto.request.CheckParkingLotRequest;
import com.example.SmartPark.dto.request.RegisterParkingLotRequest;
import com.example.SmartPark.dto.response.ParkingLotAvailabilityResponse;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.dto.response.VehicleResponse;
import com.example.SmartPark.service.interfaces.ParkingLotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //Check out of parking lot
    @PostMapping("/check-out")
    public Response<Void> checkOutVehicle(@RequestBody @Valid CheckOutRequest checkOutRequest) {
        return parkingLotService.checkOut(checkOutRequest.getLicensePlate());
    }

    //Get availability and occupancy
    @GetMapping("/availability")
    public Response<ParkingLotAvailabilityResponse> getAvailability(@RequestBody @Valid CheckParkingLotRequest checkParkingLotRequest) {
        return parkingLotService.checkParkingLotAvailability(checkParkingLotRequest.getLotId());
    }

    //List all vehicles in a parking lot
    @GetMapping("/vehicles")
    public Response<List<VehicleResponse>> getVehicles(@RequestBody @Valid CheckParkingLotRequest checkParkingLotRequest) {
        return parkingLotService.getVehicles(checkParkingLotRequest.getLotId());
    }
}
