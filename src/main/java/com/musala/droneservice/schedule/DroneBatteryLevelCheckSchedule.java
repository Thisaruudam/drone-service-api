package com.musala.droneservice.schedule;

import com.musala.droneservice.entity.Drone;
import com.musala.droneservice.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class DroneBatteryLevelCheckSchedule {

    private final DroneRepository droneRepository;

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void logDroneBatteryLevel() {
        List<Drone> drones = droneRepository.findAll();
        log.info("============================");
        drones.forEach(drone -> log.info(String.format("Drone Battery level [ %s - %s ]", drone.getSerialNumber(),
                drone.getBatteryCapacity())));
        log.info("============================");

    }

}
