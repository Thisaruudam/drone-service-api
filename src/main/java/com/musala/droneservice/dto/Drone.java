package com.musala.droneservice.dto;

import com.musala.droneservice.enums.State;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class Drone implements Serializable {

    @Size(min = 1, max = 100, message = "Serial number should be less than 100 characters")
    private String serialNumber;
    private String model;
    @Max(value = 500, message = "Weight limit should be less than 500g")
    private double weightLimit;
    @Max(value = 100, message = "Battery capacity should be less than or equal 100%")
    private int batteryCapacity;
    private State state;

}
