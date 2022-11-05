package com.musala.droneservice.service.impl;

import com.musala.droneservice.dto.Drone;
import com.musala.droneservice.enums.State;
import com.musala.droneservice.repository.DroneRepository;
import com.musala.droneservice.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS)
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;

    @Value("${drone.service.api.min.battery.level}")
    private int minBatteryLevel;

    @Override
    public Drone saveOrUpdate(Drone drone) {

        com.musala.droneservice.entity.Drone droneEntity = convertDroneDtoToDrone(drone);
        Optional<com.musala.droneservice.entity.Drone> droneById = droneRepository.findById(drone.getSerialNumber());
        droneById.ifPresent(droneExists -> droneEntity.setSerialNumber(droneExists.getSerialNumber()));

        return convertDroneToDroneDto(droneRepository.save(droneEntity));
    }

    @Override
    public List<Drone> getAllAvailableDrones() {

        List<com.musala.droneservice.entity.Drone> droneList = droneRepository.getDronesByStateAndBatteryCapacityIsGreaterThanEqual(State.IDLE, minBatteryLevel);
        List<Drone> drones = new ArrayList<>();
        droneList.forEach(drone -> drones.add(convertDroneToDroneDto(drone)));

        return drones;
    }

    @Override
    public int getBatteryLevelByDrone(String serialNumber) {
        return droneRepository.getDroneBatteryCapacityBySerialNumber(serialNumber);
    }

    @Override
    public List<Drone> getAllDrones() {

        List<com.musala.droneservice.entity.Drone> droneList = droneRepository.findAll();
        List<Drone> drones = new ArrayList<>();
        droneList.forEach(drone -> drones.add(convertDroneToDroneDto(drone)));

        return drones;
    }

    private com.musala.droneservice.entity.Drone convertDroneDtoToDrone(Drone drone) {

        com.musala.droneservice.entity.Drone droneEntity = new com.musala.droneservice.entity.Drone();
        BeanUtils.copyProperties(drone, droneEntity);
        return droneEntity;

    }

    private Drone convertDroneToDroneDto(com.musala.droneservice.entity.Drone drone) {

        Drone droneDto = new Drone();
        BeanUtils.copyProperties(drone, droneDto);
        return droneDto;

    }

}
