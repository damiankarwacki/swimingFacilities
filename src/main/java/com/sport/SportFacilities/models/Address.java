package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Pattern(regexp = "^[A-Z0-9][A-Za-z0-9|\\s]*", message = "{validation.properName}")
    @NonNull
    @Getter
    private String street;

    @Pattern(regexp = "^[A-Z][a-z]+", message = "{validation.city}")
    @NonNull
    @Getter
    private String city;

    @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "{validation.postCode}")
    @NonNull
    @Getter
    private String postCode;

    @OneToOne(mappedBy = "address")
    private SportObject sportObject;
    
    public Address(Integer id, Address address) {
        this.id = id;
        this.street = address.getStreet();
        this.city = address.getCity();
        this.postCode = address.getPostCode();
    }
}
