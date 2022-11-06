package com.musala.droneservice.controller;

import com.musala.droneservice.dto.Load;
import com.musala.droneservice.dto.Medication;
import com.musala.droneservice.service.LoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/loads")
public class LoadController {

    private final LoadService loadService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Load> loadDroneWithMedication(@Valid @RequestBody Load load) throws Exception {
        return new ResponseEntity<>(loadService.loadDroneWithMedication(load), HttpStatus.CREATED);
    }

    @GetMapping(value = "{serialNumber}/medications", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Medication>> getLoadedMedicationsByDrone(@PathVariable String serialNumber) throws Exception {
        return new ResponseEntity<>(loadService.getLoadedMedicationsByDrone(serialNumber), HttpStatus.OK);
    }

}
