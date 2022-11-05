package com.musala.droneservice.controller;

import com.musala.droneservice.dto.Drone;
import com.musala.droneservice.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/drones")
public class DroneController {

    private final DroneService droneService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Drone> registerDroneOrUpdate(@Valid @RequestBody Drone drone) throws Exception {
        return new ResponseEntity<>(droneService.saveOrUpdate(drone), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Drone>> getAllDrones() throws Exception {
        return ResponseEntity.ok(droneService.getAllDrones());
    }

    @GetMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Drone>> getAvailableDrones() throws Exception {
        return ResponseEntity.ok(droneService.getAllAvailableDrones());
    }

    @GetMapping(value = "{serialNumber}/battery-capacity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getDroneBatteryCapacity(@PathVariable String serialNumber) throws Exception {
        return ResponseEntity.ok(droneService.getBatteryLevelByDrone(serialNumber));
    }

}
