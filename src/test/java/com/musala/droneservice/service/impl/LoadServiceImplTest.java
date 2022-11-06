package com.musala.droneservice.service.impl;

import com.musala.droneservice.dto.Drone;
import com.musala.droneservice.entity.Load;
import com.musala.droneservice.entity.Medication;
import com.musala.droneservice.enums.State;
import com.musala.droneservice.exception.DroneLoadingException;
import com.musala.droneservice.repository.DroneRepository;
import com.musala.droneservice.repository.LoadRepository;
import com.musala.droneservice.repository.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoadServiceImplTest {

    @Mock
    private LoadRepository loadRepository;

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private LoadServiceImpl loadService;

    @Test
    void loadDroneWithMedication_DroneNull() {
        com.musala.droneservice.dto.Load load = new com.musala.droneservice.dto.Load();
        DroneLoadingException exception = assertThrows(DroneLoadingException.class, () -> loadService.loadDroneWithMedication(load));
        assertEquals("Drone need to be specified.", exception.getMessage());
    }

    @Test
    void loadDroneWithMedication_MedicationNull() {
        com.musala.droneservice.dto.Load load = new com.musala.droneservice.dto.Load();
        Drone drone = new Drone();
        drone.setSerialNumber("TEST");
        load.setDrone(drone);
        DroneLoadingException exception = assertThrows(DroneLoadingException.class, () -> loadService.loadDroneWithMedication(load));
        assertEquals("Medication must be added.", exception.getMessage());
    }

    @Test
    void loadDroneWithMedication_DroneWeighLimitExceed() {
        com.musala.droneservice.dto.Load load = new com.musala.droneservice.dto.Load();
        Drone drone = new Drone();
        List<com.musala.droneservice.dto.Medication> medications = new ArrayList<>();
        drone.setSerialNumber("TEST");
        drone.setWeightLimit(100);
        load.setDrone(drone);

        com.musala.droneservice.dto.Medication medication = new com.musala.droneservice.dto.Medication();
        medication.setWeight(1000);
        medications.add(medication);

        load.setMedications(medications);

        DroneLoadingException exception = assertThrows(DroneLoadingException.class, () -> loadService.loadDroneWithMedication(load));
        assertEquals("Drone weight limit exceeded. Please lower your weight and try again.", exception.getMessage());
    }

    @Test
    void loadDroneWithMedication_DroneBatteryCapacityLow() {
        com.musala.droneservice.dto.Load load = new com.musala.droneservice.dto.Load();
        Drone drone = new Drone();
        List<com.musala.droneservice.dto.Medication> medications = new ArrayList<>();
        drone.setSerialNumber("TEST");
        drone.setWeightLimit(100);
        load.setDrone(drone);

        com.musala.droneservice.dto.Medication medication = new com.musala.droneservice.dto.Medication();
        medication.setWeight(50);
        medications.add(medication);

        load.setMedications(medications);

        com.musala.droneservice.entity.Drone droneEntity = new com.musala.droneservice.entity.Drone();
        droneEntity.setBatteryCapacity(24);
        Mockito.when(droneRepository.getReferenceById(ArgumentMatchers.any())).thenReturn(droneEntity);
        ReflectionTestUtils.setField(loadService, "minBatteryLevel", 25);

        DroneLoadingException exception = assertThrows(DroneLoadingException.class, () -> loadService.loadDroneWithMedication(load));
        assertEquals("Drone cannot be loaded. Battery is less than 25%", exception.getMessage());
    }

    @Test
    void loadDroneWithMedication_DroneAlreadyLoaded() {
        com.musala.droneservice.dto.Load load = new com.musala.droneservice.dto.Load();
        Drone drone = new Drone();
        List<com.musala.droneservice.dto.Medication> medications = new ArrayList<>();
        drone.setSerialNumber("TEST");
        drone.setWeightLimit(100);
        load.setDrone(drone);

        com.musala.droneservice.dto.Medication medication = new com.musala.droneservice.dto.Medication();
        medication.setWeight(50);
        medications.add(medication);

        load.setMedications(medications);

        com.musala.droneservice.entity.Drone droneEntity = new com.musala.droneservice.entity.Drone();
        droneEntity.setBatteryCapacity(50);
        droneEntity.setState(State.LOADED);
        Mockito.when(droneRepository.getReferenceById(ArgumentMatchers.any())).thenReturn(droneEntity);
        ReflectionTestUtils.setField(loadService, "minBatteryLevel", 25);

        DroneLoadingException exception = assertThrows(DroneLoadingException.class, () -> loadService.loadDroneWithMedication(load));
        assertEquals("Drone cannot be loaded. Already loaded", exception.getMessage());
    }

    @Test
    void loadDroneWithMedication_MedicationAlreadyExists() {
        com.musala.droneservice.dto.Load load = new com.musala.droneservice.dto.Load();
        Drone drone = new Drone();
        List<com.musala.droneservice.dto.Medication> medications = new ArrayList<>();
        drone.setSerialNumber("TEST");
        drone.setWeightLimit(100);
        load.setDrone(drone);

        com.musala.droneservice.dto.Medication medication = new com.musala.droneservice.dto.Medication();
        medication.setWeight(50);
        medications.add(medication);

        load.setMedications(medications);

        com.musala.droneservice.entity.Drone droneEntity = new com.musala.droneservice.entity.Drone();
        droneEntity.setBatteryCapacity(50);
        droneEntity.setState(State.IDLE);
        Mockito.when(droneRepository.getReferenceById(ArgumentMatchers.any())).thenReturn(droneEntity);
        ReflectionTestUtils.setField(loadService, "minBatteryLevel", 25);

        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(new Medication());

        Mockito.when(medicationRepository.findAllById(ArgumentMatchers.any())).thenReturn(medicationList);

        DroneLoadingException exception = assertThrows(DroneLoadingException.class, () -> loadService.loadDroneWithMedication(load));
        assertEquals("Medications Already Exists. Please enter a new code and try again", exception.getMessage());
    }

    @Test
    void loadDroneWithMedication() {
        com.musala.droneservice.dto.Load load = new com.musala.droneservice.dto.Load();
        Drone drone = new Drone();
        List<com.musala.droneservice.dto.Medication> medications = new ArrayList<>();
        drone.setSerialNumber("TEST");
        drone.setWeightLimit(100);
        load.setDrone(drone);

        com.musala.droneservice.dto.Medication medication = new com.musala.droneservice.dto.Medication();
        medication.setWeight(50);
        medications.add(medication);

        load.setMedications(medications);

        com.musala.droneservice.entity.Drone droneEntity = new com.musala.droneservice.entity.Drone();
        droneEntity.setSerialNumber("TEST");
        droneEntity.setBatteryCapacity(50);
        droneEntity.setState(State.IDLE);
        Mockito.when(droneRepository.getReferenceById(ArgumentMatchers.any())).thenReturn(droneEntity);
        ReflectionTestUtils.setField(loadService, "minBatteryLevel", 25);

        List<Medication> medicationList = new ArrayList<>();

        Load loadEntity = new Load();
        loadEntity.setDrone(droneEntity);
        loadEntity.setMedications(medicationList);

        Mockito.when(medicationRepository.findAllById(ArgumentMatchers.any())).thenReturn(medicationList);
        Mockito.when(loadRepository.save(ArgumentMatchers.any())).thenReturn(loadEntity);

        assertEquals(loadService.loadDroneWithMedication(load).getDrone().getSerialNumber(), drone.getSerialNumber());
    }

    @Test
    void getLoadedMedicationsByDrone() {

        Load load = new Load();
        Medication medication = new Medication();
        medication.setCode("TEST");

        List<Medication> medications = new ArrayList<>();
        medications.add(medication);

        load.setMedications(medications);

        Mockito.when(loadRepository.findLoadByDrone_SerialNumber(ArgumentMatchers.any())).thenReturn(load);

        List<com.musala.droneservice.dto.Medication> medicationsByDrone = loadService.getLoadedMedicationsByDrone("");

        assertTrue(medicationsByDrone.stream().allMatch(medication1 -> medication1.getCode().equals(medication.getCode())));

    }
}
