package com.musala.droneservice.controller;

import com.musala.droneservice.dto.Drone;
import com.musala.droneservice.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/drones")
public class DroneController {

    private final DroneService droneService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Drone> registerDroneOrUpdate(@RequestBody Drone drone) throws Exception {
        return new ResponseEntity<>(droneService.saveOrUpdate(drone), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDrones()
    {
        return "works fine";
    }

}
