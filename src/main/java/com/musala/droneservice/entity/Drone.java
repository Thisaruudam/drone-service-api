package com.musala.droneservice.entity;

import com.musala.droneservice.enums.State;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
public class Drone {

    @Id
    @Column(length = 100)
    private String serialNumber;
    private String model;
    private double weightLimit;
    private int batteryCapacity;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private State state;

}
