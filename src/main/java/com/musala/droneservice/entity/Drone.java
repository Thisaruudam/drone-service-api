package com.musala.droneservice.entity;

import com.musala.droneservice.enums.State;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Drone {

    @Id
    @Column(length = 100)
    private String serialNumber;
    private String model;
    @Column(precision = 3, scale = 2)
    private double weightLimit;
    private int batteryCapacity;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private State state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Drone drone = (Drone) o;
        return serialNumber != null && Objects.equals(serialNumber, drone.serialNumber);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
