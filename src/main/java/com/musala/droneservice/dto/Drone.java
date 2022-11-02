package com.musala.droneservice.dto;

import com.musala.droneservice.enums.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Drone {

    private String serialNumber;
    private String model;
    private double weightLimit;
    private int batteryCapacity;
    private State state;

}
