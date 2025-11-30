package com.example.SmartPark.dto.request;

import com.example.SmartPark.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class RegisterVehicleRequest {

    @NotBlank(message = "License Plate is required")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License Plate can only contain letters, numbers, and dashes")
    String licensePlate;

    @NotNull(message = "Vehicle Type must not be null")
    VehicleType vehicleType;

    @NotBlank(message = "Owner Name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Owner can only contain letters and spaces")
    String ownerName;

}
