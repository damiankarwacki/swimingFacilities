package com.sport.SportFacilities.models;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
