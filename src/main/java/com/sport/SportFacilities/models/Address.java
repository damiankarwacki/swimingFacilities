package com.sport.SportFacilities.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Address {
    
    @Id
    @GeneratedValue
    private Integer id;
    @NonNull
    private String street;
    @NonNull
    private String city;
    @NonNull
    private String postCode;
    
    @OneToOne(mappedBy = "address")
    private SportObject sportObject;
}
