package com.musala.droneservice.service;

import com.musala.droneservice.dto.Drone;

public interface DroneService {

    Drone saveOrUpdate(Drone drone) throws Exception;

}
