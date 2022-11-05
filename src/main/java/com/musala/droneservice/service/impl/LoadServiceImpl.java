package com.musala.droneservice.service.impl;

import com.musala.droneservice.dto.Load;
import com.musala.droneservice.entity.Drone;
import com.musala.droneservice.entity.Medication;
import com.musala.droneservice.enums.State;
import com.musala.droneservice.exception.DroneLoadingException;
import com.musala.droneservice.repository.DroneRepository;
import com.musala.droneservice.repository.LoadRepository;
import com.musala.droneservice.repository.MedicationRepository;
import com.musala.droneservice.service.LoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class LoadServiceImpl implements LoadService {

    private final LoadRepository loadRepository;
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    @Value("${drone.service.api.min.battery.level}")
    private int minBatteryLevel;

    @Override
    public Load loadDroneWithMedication(Load load) throws DroneLoadingException {

        if(Objects.isNull(load.getDrone().getSerialNumber())) {
            throw new DroneLoadingException("Drone need to be specified.");
        }

        if(Objects.isNull(load.getMedications())) {
            throw new DroneLoadingException("Medication must be added.");
        }

        double maxWeight = load.getMedications().stream()
                .mapToDouble(com.musala.droneservice.dto.Medication::getWeight).sum();

        if(maxWeight > load.getDrone().getWeightLimit() ) {
            throw new DroneLoadingException("Drone weight limit exceeded. Please lower your weight and try again.");
        }

        Drone drone = droneRepository.getReferenceById(load.getDrone().getSerialNumber());

        if(minBatteryLevel > drone.getBatteryCapacity()) {
            throw new DroneLoadingException("Drone cannot be loaded. Battery is less than 25%");
        }

        if(State.IDLE != drone.getState()) {
            throw new DroneLoadingException("Drone cannot be loaded. Already loaded");
        }

        List<String> medicationCodes = load.getMedications().stream().map(com.musala.droneservice.dto.Medication::getCode).collect(Collectors.toList());
        List<Medication> list = medicationRepository.findAllById(medicationCodes);

        if(!list.isEmpty()) {
            throw new DroneLoadingException("Medications Already Exists. Please enter a new code and try again");
        }

        // Set drone state while loading
        droneRepository.setDroneStateByDroneSerialNumber(State.LOADING, drone.getSerialNumber());

        com.musala.droneservice.entity.Load loadEntity = new com.musala.droneservice.entity.Load();
        loadEntity.setTrackingNumber(String.valueOf(UUID.randomUUID()));
        loadEntity.setOrigin(load.getOrigin());
        loadEntity.setDestination(load.getDestination());
        loadEntity.setLoadedDateTime(LocalDateTime.now());
        loadEntity.setDrone(drone);
        loadEntity.setMedications(convertMedicationsDtoToEntity(load.getMedications()));

        // Set drone state after loading
        drone.setState(State.LOADED);

        return convertPackageToDto(loadRepository.save(loadEntity));
    }

    @Override
    public List<com.musala.droneservice.dto.Medication> getLoadedMedicationsByDrone(String serialNumber) {

        com.musala.droneservice.entity.Load loadByDroneSerialNumber =
                loadRepository.findLoadByDrone_SerialNumber(serialNumber);

        if(null != loadByDroneSerialNumber) {
            return convertMedicationsToDto(loadByDroneSerialNumber.getMedications());
        } else {
            return new ArrayList<>();
        }
    }

    private List<Medication> convertMedicationsDtoToEntity(List<com.musala.droneservice.dto.Medication> medications) {
        List<Medication> medicationEntities = new ArrayList<>();

        medications.forEach(medicationDto -> {
            Medication medicationEntity = new Medication();
            BeanUtils.copyProperties(medicationDto, medicationEntity);
            medicationEntities.add(medicationEntity);
        });

        return medicationEntities;
    }

    private List<com.musala.droneservice.dto.Medication> convertMedicationsToDto(List<Medication> medications) {
        List<com.musala.droneservice.dto.Medication> medicationDtos = new ArrayList<>();

        medications.forEach(medication -> {
            com.musala.droneservice.dto.Medication medicationDto = new com.musala.droneservice.dto.Medication();
            BeanUtils.copyProperties(medication, medicationDto);
            medicationDtos.add(medicationDto);
        });

        return medicationDtos;
    }

    private com.musala.droneservice.dto.Drone convertDroneToDroneDto(com.musala.droneservice.entity.Drone drone) {
        com.musala.droneservice.dto.Drone droneDto = new com.musala.droneservice.dto.Drone();
        BeanUtils.copyProperties(drone, droneDto);
        return droneDto;
    }

    private Load convertPackageToDto(com.musala.droneservice.entity.Load load) {

        Load loadDto = new Load();
        com.musala.droneservice.dto.Drone drone = convertDroneToDroneDto(load.getDrone());
        loadDto.setTrackingNumber(load.getTrackingNumber());
        loadDto.setOrigin(load.getOrigin());
        loadDto.setDestination(load.getDestination());
        loadDto.setLoadedDateTime(load.getLoadedDateTime());
        loadDto.setDrone(drone);
        loadDto.setMedications(convertMedicationsToDto(load.getMedications()));

        return loadDto;
    }

}
