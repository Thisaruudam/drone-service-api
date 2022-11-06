package com.musala.droneservice.repository;

import com.musala.droneservice.entity.Load;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadRepository extends JpaRepository<Load, String> {

    Load findLoadByDrone_SerialNumber(String serialNumber);

}
