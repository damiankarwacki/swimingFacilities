package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    @Getter
    private Integer id;
    @NonNull
    @Getter
    private String street;
    @NonNull
    @Getter
    private String city;
    @NonNull
    @Getter
    private String postCode;

//    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private SportObject sportObject;
    
    public Address(Integer id, Address address) {
        this.id = id;
        this.street = address.getStreet();
        this.city = address.getCity();
        this.postCode = address.getPostCode();
    }
}
