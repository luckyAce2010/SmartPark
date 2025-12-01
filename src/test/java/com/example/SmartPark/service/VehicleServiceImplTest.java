package com.example.SmartPark.service;

import com.example.SmartPark.data.VehicleData;
import com.example.SmartPark.dto.request.RegisterVehicleRequest;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.enums.VehicleType;
import com.example.SmartPark.pojo.Vehicle;
import com.example.SmartPark.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static com.example.SmartPark.constants.ResponseConstant.BAD_REQUEST_CODE;
import static com.example.SmartPark.constants.ResponseConstant.SUCCESS_CODE;
import static com.example.SmartPark.constants.VehicleConstant.VEHICLE_LICENSE_PLATE_USED;
import static com.example.SmartPark.constants.VehicleConstant.VEHICLE_REGISTER_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceImplTest {

    @Mock
    private VehicleData vehicleData;

    private VehicleServiceImpl service;

    //Request
    private RegisterVehicleRequest registerVehicleRequest;

    //Variables
    private final String licensePlate = "A1";
    private final String ownerName = "Ace";


    //Objects
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        service = new VehicleServiceImpl(vehicleData);


        //Build objects
        vehicle = Vehicle.builder()
                .licensePlate(licensePlate)
                .ownerName(ownerName)
                .vehicleType(VehicleType.MOTORCYCLE)
                .checkedIn(false)
                .build();

        //Set valid req
        registerVehicleRequest = RegisterVehicleRequest.builder()
                .licensePlate(licensePlate)
                .ownerName(ownerName)
                .vehicleType(VehicleType.MOTORCYCLE)
                .build();
    }

    @Test
    void passRegisterVehicle(){
        Mockito.when(vehicleData.licensePlateExists(licensePlate))
                .thenReturn(false);

        Response<Void> result = service.registerVehicle(registerVehicleRequest);

        assertEquals(SUCCESS_CODE, result.getStatusCode());
        assertEquals(VEHICLE_REGISTER_SUCCESS, result.getMessage());
        // Verify that the vehicle was actually registered
        Mockito.verify(vehicleData).registerVehicle(Mockito.argThat(vehicle ->
                Objects.equals(this.vehicle, vehicle)
        ));
    }

    @Test
    void failWhenLicensePlateAlreadyExists() {
        Mockito.when(vehicleData.licensePlateExists(licensePlate)).thenReturn(true);

        Response<Void> result = service.registerVehicle(registerVehicleRequest);

        assertEquals(BAD_REQUEST_CODE, result.getStatusCode());
        assertEquals(VEHICLE_LICENSE_PLATE_USED(licensePlate), result.getMessage());
    }

}
