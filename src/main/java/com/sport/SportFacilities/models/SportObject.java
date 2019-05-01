package com.sport.SportFacilities.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
public class SportObject {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @OneToMany(mappedBy = "sportObject")
    private Set<SwimmingPool> swimmingPool;

    private Address address;
    private String name;
}
