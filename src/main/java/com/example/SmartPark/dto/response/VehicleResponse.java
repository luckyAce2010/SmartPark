package com.example.SmartPark.dto.response;

import com.example.SmartPark.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class VehicleResponse {
    String licensePlate;
    VehicleType vehicleType;
    String ownerName;
}
