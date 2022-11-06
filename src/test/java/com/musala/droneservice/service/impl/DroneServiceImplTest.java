package com.musala.droneservice.service.impl;

import com.musala.droneservice.entity.Drone;
import com.musala.droneservice.enums.State;
import com.musala.droneservice.repository.DroneRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DroneServiceImplTest {

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DroneServiceImpl droneService;

    @Test
    void saveOrUpdate() {

        Drone drone = new Drone();
        drone.setSerialNumber("TEST_SERIAL_NUMBER");
        drone.setModel("Middleweight");
        drone.setWeightLimit(400.0);
        drone.setState(State.IDLE);

        com.musala.droneservice.dto.Drone droneDto = new com.musala.droneservice.dto.Drone();
        droneDto.setSerialNumber("TEST_SERIAL_NUMBER");
        droneDto.setModel("Middleweight");
        droneDto.setWeightLimit(400.0);
        droneDto.setState(State.IDLE);

        Mockito.when(droneRepository.save(drone)).thenReturn(drone);

        Assertions.assertThat(droneService.saveOrUpdate(droneDto).getSerialNumber()).isEqualTo(drone.getSerialNumber());

    }

    @Test
    void getAllAvailableDrones() {

        Drone drone1 = new Drone();
        drone1.setSerialNumber("DJIMAVICMINI2");
        drone1.setModel("Cruiserweight");
        drone1.setWeightLimit(500.0);
        drone1.setState(State.IDLE);
        drone1.setBatteryCapacity(100);

        Drone drone2 = new Drone();
        drone2.setSerialNumber("DJIMAVICMINI3");
        drone2.setModel("Middleweight");
        drone2.setWeightLimit(400.0);
        drone2.setState(State.IDLE);
        drone2.setBatteryCapacity(100);

        List<Drone> drones = new ArrayList<>();
        drones.add(drone1);
        drones.add(drone2);

        Mockito.when(droneRepository.getDronesByStateAndBatteryCapacityIsGreaterThanEqual(ArgumentMatchers.any(), ArgumentMatchers.anyInt())).thenReturn(drones);

        Assertions.assertThat(droneService.getAllAvailableDrones()).hasSize(2);

    }

    @Test
    void getBatteryLevelByDrone() {
        Mockito.when(droneRepository.getDroneBatteryCapacityBySerialNumber(ArgumentMatchers.anyString())).thenReturn(100);
        assertEquals(100, droneService.getBatteryLevelByDrone("DJIMAVICMINI2"));
    }

    @Test
    void getAllDrones() {
        Drone drone1 = new Drone();
        drone1.setSerialNumber("DJIMAVICMINI2");
        drone1.setModel("Cruiserweight");
        drone1.setWeightLimit(500.0);
        drone1.setState(State.LOADED);
        drone1.setBatteryCapacity(100);

        Drone drone2 = new Drone();
        drone2.setSerialNumber("DJIMAVICMINI3");
        drone2.setModel("Middleweight");
        drone2.setWeightLimit(400.0);
        drone2.setState(State.IDLE);
        drone2.setBatteryCapacity(100);

        List<Drone> drones = new ArrayList<>();
        drones.add(drone1);
        drones.add(drone2);

        Mockito.when(droneRepository.findAll()).thenReturn(drones);

        Assertions.assertThat(droneService.getAllDrones()).hasSize(2);
    }
}
