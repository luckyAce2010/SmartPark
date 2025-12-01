package com.example.SmartPark.service;

import com.example.SmartPark.data.ParkingLotData;
import com.example.SmartPark.data.VehicleData;
import com.example.SmartPark.dto.request.RegisterParkingLotRequest;
import com.example.SmartPark.dto.response.ParkingLotAvailabilityResponse;
import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.dto.response.VehicleResponse;
import com.example.SmartPark.enums.VehicleType;
import com.example.SmartPark.pojo.ParkingLot;
import com.example.SmartPark.pojo.Vehicle;
import com.example.SmartPark.service.impl.ParkingLotServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.example.SmartPark.constants.ParkingLotConstant.PARKING_FULL;
import static com.example.SmartPark.constants.ParkingLotConstant.PARKING_ID_NOT_EXISTS;
import static com.example.SmartPark.constants.ResponseConstant.BAD_REQUEST_CODE;
import static com.example.SmartPark.constants.ResponseConstant.SUCCESS_CODE;
import static com.example.SmartPark.constants.VehicleConstant.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ParkingLotServiceImplTest {

    @Mock
    private ParkingLotData parkingLotData;

    @Mock
    private VehicleData vehicleData;

    private ParkingLotServiceImpl service;

    //Requests
    private RegisterParkingLotRequest registerParkingLotReq;

    //Variables
    private final String lotId = "A";
    private final String licensePlate = "A1";

    //Objects
    private Vehicle vehicle;
    private ParkingLot lot;

    @BeforeEach
    void setUp() {
        service = new ParkingLotServiceImpl(parkingLotData, vehicleData);


        //Build objects
        vehicle = Vehicle.builder()
                .licensePlate(licensePlate)
                .ownerName("Ace")
                .vehicleType(VehicleType.MOTORCYCLE)
                .checkedIn(false)
                .build();

        lot = ParkingLot.builder()
                .lotId(lotId)
                .capacity(10)
                .occupiedSpaces(0)
                .vehicles(new ArrayList<>())
                .build();

        //Set valid req
        registerParkingLotReq = new RegisterParkingLotRequest();
        registerParkingLotReq.setLotId(lotId);
        registerParkingLotReq.setCapacity(10);
        registerParkingLotReq.setLocation("Test");
    }

    //Register tests
    @Test
    void passRegisterValidParkingLot() {
        Mockito.when(parkingLotData.parkingLotIdExists("A"))
                .thenReturn(false);

        Response<Void> result = service.registerParkingLot(registerParkingLotReq);

        assertEquals(SUCCESS_CODE, result.getStatusCode());
        Mockito.verify(parkingLotData).registerParkingLot(Mockito.any());
    }

    @Test
    void failWhenLotIdAlreadyExists() {
        Mockito.when(parkingLotData.parkingLotIdExists("A")).thenReturn(true);

        Response<Void> result = service.registerParkingLot(registerParkingLotReq);

        assertEquals(BAD_REQUEST_CODE, result.getStatusCode());
    }


    //Check in tests
    @Test
    void failVehicleLicensePlateNotExists(){
        Mockito.when(vehicleData.getVehicle(licensePlate)).thenReturn(null);

        Response<Void> response = service.checkIn(lotId, licensePlate);

        assertEquals(BAD_REQUEST_CODE, response.getStatusCode());
        assertEquals(VEHICLE_LICENSE_PLATE_NOT_EXISTS(licensePlate), response.getMessage());

        response = service.checkOut(licensePlate);

        assertEquals(BAD_REQUEST_CODE, response.getStatusCode());
        assertEquals(VEHICLE_LICENSE_PLATE_NOT_EXISTS(licensePlate), response.getMessage());
    }

    @Test
    void failVehicleAlreadyCheckIn(){
        vehicle.setCheckedIn(true);
        Mockito.when(vehicleData.getVehicle(licensePlate)).thenReturn(vehicle);

        Response<Void> response = service.checkIn(lotId, licensePlate);

        assertEquals(BAD_REQUEST_CODE, response.getStatusCode());
        assertEquals(VEHICLE_ALREADY_CHECKED_IN, response.getMessage());
    }

    @Test
    void failParkingLotIdNotExists(){
        Mockito.when(vehicleData.getVehicle(licensePlate)).thenReturn(vehicle);
        Mockito.when(parkingLotData.getParkingLot(lotId)).thenReturn(null);

        Response<Void> response = service.checkIn(lotId, licensePlate);

        assertEquals(BAD_REQUEST_CODE, response.getStatusCode());
        assertEquals(PARKING_ID_NOT_EXISTS(lotId), response.getMessage());

        Response<ParkingLotAvailabilityResponse> response1 = service.checkParkingLotAvailability(lotId);

        assertEquals(BAD_REQUEST_CODE, response1.getStatusCode());
        assertEquals(PARKING_ID_NOT_EXISTS(lotId), response1.getMessage());

        Response<List<VehicleResponse>> response2 = service.getVehicles(lotId);

        assertEquals(BAD_REQUEST_CODE, response2.getStatusCode());
        assertEquals(PARKING_ID_NOT_EXISTS(lotId), response2.getMessage());
    }

    @Test
    void failParkingLotIsFull(){
        vehicle.setCheckedIn(false);
        lot.setOccupiedSpaces(10);

        Mockito.when(vehicleData.getVehicle(licensePlate)).thenReturn(vehicle);
        Mockito.when(parkingLotData.getParkingLot(lotId)).thenReturn(lot);

        Response<Void> response = service.checkIn(lotId, licensePlate);

        assertEquals(BAD_REQUEST_CODE, response.getStatusCode());
        assertEquals(PARKING_FULL, response.getMessage());
    }


    @Test
    void passCheckIn() {
        Mockito.when(vehicleData.getVehicle(licensePlate)).thenReturn(vehicle);
        Mockito.when(parkingLotData.getParkingLot(lotId)).thenReturn(lot);

        Response<Void> response = service.checkIn(lotId, licensePlate);

        assertEquals(SUCCESS_CODE, response.getStatusCode());
        assertTrue(vehicle.getCheckedIn());
        assertEquals(lotId, vehicle.getCurrentParkingLot());
        assertEquals(1, lot.getOccupiedSpaces());
        assertTrue(lot.getVehicles().contains(vehicle));
    }

    //Check out Tests
    @Test
    void failVehicleNotCheckedIn(){
        Mockito.when(vehicleData.getVehicle(licensePlate)).thenReturn(vehicle);

        Response<Void> response = service.checkOut(licensePlate);

        assertEquals(BAD_REQUEST_CODE, response.getStatusCode());
        assertEquals(VEHICLE_NOT_CHECKED_IN, response.getMessage());
    }

    @Test
    void passCheckOut() {
        vehicle.setCheckedIn(true);
        vehicle.setCurrentParkingLot(lotId);

        lot.setOccupiedSpaces(1);
        lot.getVehicles().add(vehicle);

        Mockito.when(vehicleData.getVehicle(licensePlate)).thenReturn(vehicle);
        Mockito.when(parkingLotData.getParkingLot(lotId)).thenReturn(lot);

        Response<Void> response = service.checkOut(licensePlate);

        assertEquals(SUCCESS_CODE, response.getStatusCode());
        assertNull(vehicle.getCurrentParkingLot());
        assertFalse(vehicle.getCheckedIn());
        assertEquals(0, lot.getOccupiedSpaces());
        assertFalse(lot.getVehicles().contains(vehicle));
    }


    //Parking Availability Tests
    @Test
    void passGetParkingLotAvailability(){
        vehicle.setCheckedIn(false);
        vehicle.setCurrentParkingLot(null);
        lot.getVehicles().clear();

        Mockito.when(vehicleData.getVehicle(licensePlate)).thenReturn(vehicle);
        Mockito.when(parkingLotData.getParkingLot(lotId)).thenReturn(lot);

        Response<Void> response = service.checkIn(lotId, licensePlate);
        Response<ParkingLotAvailabilityResponse> responseAvailability = service.checkParkingLotAvailability(lotId);

        assertEquals(SUCCESS_CODE, response.getStatusCode());
        assertEquals(lotId, responseAvailability.getData().getLotId());
        assertEquals(1, responseAvailability.getData().getOccupied());
        assertEquals(9, responseAvailability.getData().getAvailable());
    }

    //List Vehicles inside Parking Lot Tests
    @Test
    void passGetVehiclesListAfterCheckIn() {
        vehicle.setCheckedIn(false);
        vehicle.setCurrentParkingLot(null);
        lot.getVehicles().clear();

        Mockito.when(vehicleData.getVehicle(licensePlate)).thenReturn(vehicle);
        Mockito.when(parkingLotData.getParkingLot(lotId)).thenReturn(lot);

        service.checkIn(lotId, licensePlate);
        Response<List<VehicleResponse>> responseAvailability = service.getVehicles(lotId);

        assertEquals(SUCCESS_CODE, responseAvailability.getStatusCode());
        assertEquals(1, responseAvailability.getData().size());

        VehicleResponse actual = responseAvailability.getData().get(0);
        assertEquals(licensePlate, actual.getLicensePlate());
        assertEquals(vehicle.getVehicleType(), actual.getVehicleType());
        assertEquals(vehicle.getOwnerName(), actual.getOwnerName());
    }

}
