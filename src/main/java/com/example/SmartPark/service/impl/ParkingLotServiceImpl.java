package com.example.SmartPark.service.impl;

import com.example.SmartPark.dto.request.CreateParkingLotRequest;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.pojo.ParkingLot;
import com.example.SmartPark.service.interfaces.ParkingLotService;
import com.example.SmartPark.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.SmartPark.constants.ParkingLotConstant.PARKING_CREATED;
import static com.example.SmartPark.constants.ParkingLotConstant.PARKING_ID_USED;
import static com.example.SmartPark.constants.ResponseConstant.BAD_REQUEST_CODE;
import static com.example.SmartPark.constants.ResponseConstant.SUCCESS_CODE;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    private final List<ParkingLot> parkingLotList = new ArrayList<>();

    @Override
    public Response<Void> registerParkingLot(CreateParkingLotRequest createParkingLotReq) {

        //Trim whitespaces and unnecessary whitespaces
        String idReq = createParkingLotReq.getLotId().trim().replaceAll("\\s+", " ");

        //Check ID
        if(!checkValidParkingLotId(idReq)){
            return ResponseUtil.error(PARKING_ID_USED(idReq), BAD_REQUEST_CODE);
        }

        ParkingLot parkingLot = ParkingLot.builder()
                .lotId(idReq)
                .capacity(createParkingLotReq.getCapacity())
                .location(createParkingLotReq.getLocation())
                .build();

        parkingLotList.add(parkingLot);

        return ResponseUtil.success(PARKING_CREATED, SUCCESS_CODE);
    }


    //Helper Methods
    //If false it means that ID is already taken
    private boolean checkValidParkingLotId(String requestId){

        for(ParkingLot parkingLot : this.parkingLotList) {
            if(parkingLot.getLotId().equals(requestId)){return false;}
        }

        return true;

    }


}
