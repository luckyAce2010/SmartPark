package com.example.SmartPark.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class CheckParkingLotRequest {

    @NotBlank(message = "Parking Lot ID is required")
    @Size(max = 50, message = "Parking lot name must not exceed 50 characters")
    String lotId;

}
