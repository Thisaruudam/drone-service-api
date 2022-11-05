package com.musala.droneservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Load {

    @Id
    private String trackingNumber;
    private String origin;
    private String destination;
    private LocalDateTime loadedDateTime;
    @OneToOne(cascade = CascadeType.ALL)
    private Drone drone;
    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Medication> medications;

}
