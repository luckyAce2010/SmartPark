package com.example.SmartPark.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VehicleType {
    CAR("Car"),
    MOTORCYCLE("Motorcycle"),
    TRUCK("Truck");

    private final String value;

    VehicleType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
