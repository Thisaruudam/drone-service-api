package com.musala.droneservice.service.impl;

import com.musala.droneservice.dto.Drone;
import com.musala.droneservice.repository.DroneRepository;
import com.musala.droneservice.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS)
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;

    @Override
    public Drone saveOrUpdate(Drone drone) {

        com.musala.droneservice.entity.Drone droneEntity = convertDroneDtoToDrone(drone);
        Optional<com.musala.droneservice.entity.Drone> droneById = droneRepository.findById(drone.getSerialNumber());
        droneById.ifPresent(droneExists -> droneEntity.setSerialNumber(droneExists.getSerialNumber()));
        droneRepository.save(droneEntity);
        return drone;
    }

    private com.musala.droneservice.entity.Drone convertDroneDtoToDrone(Drone drone) {

        com.musala.droneservice.entity.Drone droneEntity = new com.musala.droneservice.entity.Drone();
        BeanUtils.copyProperties(drone, droneEntity);
        return droneEntity;

    }

    private Drone convertDroneToDroneDto(com.musala.droneservice.entity.Drone drone) {

        Drone droneDto = new Drone();
        BeanUtils.copyProperties(droneDto, drone);
        return droneDto;

    }

}
