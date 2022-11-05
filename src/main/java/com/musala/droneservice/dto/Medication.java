package com.musala.droneservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class Medication {

    @Pattern(regexp = "^[A-Z0-9_]*$")
    private String code;
    @Pattern(regexp = "^[a-zA-Z0-9\\-_]*$")
    private String name;
    private double weight;
    private String image;

}
