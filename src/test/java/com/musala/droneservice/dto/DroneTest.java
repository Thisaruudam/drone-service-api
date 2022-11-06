package com.musala.droneservice.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DroneTest {

    private Validator validator;

    @BeforeEach
    public void init() {

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();

    }

    @Test
    void setSerialNumber() {

        Drone drone1 = new Drone();
        drone1.setSerialNumber("");

        Set<ConstraintViolation<Drone>> violations1 = validator.validate(drone1);
        assertFalse(violations1.isEmpty());

        Drone drone2 = new Drone();
        drone2.setSerialNumber("asdas dasyjghd asdhjas dagjsd vhagsvdh asgdv aghsvdayjsd aysd " +
                "ajshdg yuasgd agshjvd ghasv dgjas dgavsd ga sdgvhgasdvaghjsd vghasvd agsdvgasvdgvasghdvghavsdgvagsvdgavsgdagsvdg");

        Set<ConstraintViolation<Drone>> violations2 = validator.validate(drone2);
        assertFalse(violations2.isEmpty());

    }

    @Test
    void setWeightLimit() {
        Drone drone1 = new Drone();
        drone1.setWeightLimit(501);

        Set<ConstraintViolation<Drone>> violations1 = validator.validate(drone1);
        assertFalse(violations1.isEmpty());

    }

    @Test
    void setBatteryCapacity() {
        Drone drone1 = new Drone();
        drone1.setBatteryCapacity(102);

        Set<ConstraintViolation<Drone>> violations1 = validator.validate(drone1);
        assertFalse(violations1.isEmpty());
    }
}
