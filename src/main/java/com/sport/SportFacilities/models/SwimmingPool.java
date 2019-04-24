package com.sport.SportFacilities.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class SwimmingPool {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    private Integer lanesQuantity;
    private Float lenght;
    private Float depth;
    
    @OneToMany(mappedBy = "swimmingPool")
    private Instructor instructor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimming_pool_id")
    private SportObject sportObject;
}
