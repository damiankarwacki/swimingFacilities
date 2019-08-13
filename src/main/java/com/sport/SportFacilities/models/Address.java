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
    @Setter
    private String street;
    @NonNull
    @Getter
    @Setter
    private String city;
    @NonNull
    @Getter
    @Setter
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
