package com.sport.SportFacilities.models;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
public class Address {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String street;
    private String city;
    private String postCode;

    @OneToOne(mappedBy = "address")
    private SportObject sportObject;
}
