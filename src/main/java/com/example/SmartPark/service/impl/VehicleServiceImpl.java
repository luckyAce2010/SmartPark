package com.example.SmartPark.service.impl;

import com.example.SmartPark.data.VehicleData;
import com.example.SmartPark.dto.request.RegisterVehicleRequest;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.pojo.Vehicle;
import com.example.SmartPark.service.interfaces.VehicleService;
import com.example.SmartPark.utils.ResponseUtil;
import com.example.SmartPark.utils.StringUtils;
import org.springframework.stereotype.Service;

import static com.example.SmartPark.constants.ResponseConstant.BAD_REQUEST_CODE;
import static com.example.SmartPark.constants.ResponseConstant.SUCCESS_CODE;
import static com.example.SmartPark.constants.VehicleConstant.VEHICLE_LICENSE_PLATE_USED;
import static com.example.SmartPark.constants.VehicleConstant.VEHICLE_REGISTER_SUCCESS;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleData vehicleData;

    public VehicleServiceImpl(VehicleData vehicleData) {
        this.vehicleData = vehicleData;
    }

    @Override
    public Response<Void> registerVehicle(RegisterVehicleRequest registerVehicleReq) {

        //Trim whitespaces and unnecessary whitespaces
        String idReq = StringUtils.removeUnnecessaryWhiteSpaces(registerVehicleReq.getLicensePlate());

        //Check ID
        if(vehicleData.licensePlateExists(idReq)){
            return ResponseUtil.error(VEHICLE_LICENSE_PLATE_USED(idReq), BAD_REQUEST_CODE);
        }

        Vehicle vehicle = Vehicle.builder()
                .licensePlate(idReq)
                .ownerName(registerVehicleReq.getOwnerName())
                .vehicleType(registerVehicleReq.getVehicleType())
                .checkedIn(false)
                .build();

        vehicleData.registerVehicle(vehicle);

        return ResponseUtil.success(VEHICLE_REGISTER_SUCCESS, SUCCESS_CODE);
    }

}
