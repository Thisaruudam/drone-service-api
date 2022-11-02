package com.musala.droneservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Medication {

    @Id
    private String code;
    private String name;
    private double weight;
    private String image;

}
