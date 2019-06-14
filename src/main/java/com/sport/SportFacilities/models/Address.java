package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;

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

//    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private SportObject sportObject;
}
