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
    
    
    @OneToMany(mappedBy = "sportObject", fetch = FetchType.LAZY)
    private Set<SwimmingPool> swimmingPool;

    @OneToOne
    private Address address;

    private String name;
}
