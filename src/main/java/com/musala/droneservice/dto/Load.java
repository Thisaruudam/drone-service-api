package com.musala.droneservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Load {

    private String trackingNumber;
    private String origin;
    private String destination;
    private LocalDateTime loadedDateTime;
    private Drone drone;
    private List<Medication> medications;

}
