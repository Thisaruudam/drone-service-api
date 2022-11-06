package com.musala.droneservice.repository;

import com.musala.droneservice.entity.Drone;
import com.musala.droneservice.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, String> {

    List<Drone> getDronesByStateAndBatteryCapacityIsGreaterThanEqual(State state, int batteryCapacity);

    @Query("SELECT batteryCapacity FROM Drone WHERE serialNumber = ?1")
    Integer getDroneBatteryCapacityBySerialNumber(String serialNumber);

    @Modifying
    @Query("UPDATE Drone SET state=?1 WHERE serialNumber=?2")
    void setDroneStateByDroneSerialNumber(State state, String serialNumber);

}
