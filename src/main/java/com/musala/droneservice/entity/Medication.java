package com.musala.droneservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class Medication {

    @Id
    private String code;
    private String name;
    private double weight;
    private String image;

}
