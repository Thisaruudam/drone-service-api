package com.musala.droneservice.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MedicationTest {

    private Validator validator;

    @BeforeEach
    public void init() {

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();

    }

    @Test
    void setCode() {

        Medication medication1 = new Medication();
        medication1.setCode("5423atdgvsd()");

        Set<ConstraintViolation<Medication>> violations1 = validator.validate(medication1);
        assertFalse(violations1.isEmpty());

        Medication medication2 = new Medication();
        medication2.setCode("5423ADAJKH_");

        Set<ConstraintViolation<Medication>> violations2 = validator.validate(medication2);
        assertTrue(violations2.isEmpty());

    }

    @Test
    void setName() {
        Medication medication1 = new Medication();
        medication1.setName("5423atdgvsd()");

        Set<ConstraintViolation<Medication>> violations1 = validator.validate(medication1);
        assertFalse(violations1.isEmpty());

        Medication medication2 = new Medication();
        medication2.setName("5423ADAJKHaswd_-");

        Set<ConstraintViolation<Medication>> violations2 = validator.validate(medication2);
        assertTrue(violations2.isEmpty());
    }
}
