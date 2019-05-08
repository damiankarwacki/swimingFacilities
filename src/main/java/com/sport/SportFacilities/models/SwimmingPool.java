package com.sport.SportFacilities.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
public class SwimmingPool {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    private Integer lanesQuantity;
    private Float lenght;
    private Float depth;
    
    @OneToMany(mappedBy = "swimmingPool",
                  fetch = FetchType.LAZY)
    private Set<Instructor> instructors;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimming_pool_id")
    private SportObject sportObject;
    
    @OneToMany(mappedBy = "swimmingPool",
                  fetch = FetchType.LAZY)
    private Set<Lesson> lessons;
    
    
}
