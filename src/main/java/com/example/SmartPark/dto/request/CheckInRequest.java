package com.example.SmartPark.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class CheckInRequest {

    @NotBlank(message = "Parking Lot ID is required")
    @Size(max = 50, message = "Parking lot name must not exceed 50 characters")
    String lotId;

    @NotBlank(message = "License Plate is required")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License Plate can only contain letters, numbers, and dashes")
    String licensePlate;
}
