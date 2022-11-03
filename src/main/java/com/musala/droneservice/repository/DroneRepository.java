package com.musala.droneservice.repository;

import com.musala.droneservice.entity.Drone;
import com.musala.droneservice.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, String> {

    List<Drone> getDronesByStateAndBatteryCapacityIsGreaterThanEqual(State state, int batteryCapacity);

}
