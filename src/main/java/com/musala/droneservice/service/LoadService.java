package com.musala.droneservice.service;

import com.musala.droneservice.dto.Load;
import com.musala.droneservice.dto.Medication;
import com.musala.droneservice.exception.DroneLoadingException;

import java.util.List;

public interface LoadService {

    Load loadDroneWithMedication(Load load) throws DroneLoadingException;

    List<Medication> getLoadedMedicationsByDrone(String serialNumber) throws Exception;

}
