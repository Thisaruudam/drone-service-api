package com.musala.droneservice.service;

import com.musala.droneservice.dto.Drone;

import java.util.List;

public interface DroneService {

    Drone saveOrUpdate(Drone drone) throws Exception;

    List<Drone> getAllAvailableDrones() throws Exception;

}
