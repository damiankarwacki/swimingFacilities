package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

//TODO Krzychu, walidacja pól
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class SwimmingPool {
    
    @Id
    @Getter
    @GeneratedValue
    private Integer id;
    
    @Getter
    @NonNull
    private Integer lanesQuantity;
    
    @Getter
    @NonNull
    private Float lenght;
    
    @Getter
    @NonNull
    private Float depth;
    
    @OneToMany(mappedBy = "swimmingPool",
                  fetch = FetchType.LAZY)
    private Set<Instructor> instructors;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "swimming_pool_id")
    @Getter
    private SportObject sportObject;
    
    @OneToMany(mappedBy = "swimmingPool",
                  fetch = FetchType.LAZY)
    private Set<Lesson> lessons;

    public SwimmingPool(Integer id, SwimmingPool swimmingPool){
        this.id = id;
        this.lanesQuantity = swimmingPool.getLanesQuantity();
        this.lenght = swimmingPool.getLenght();
        this.depth = swimmingPool.getDepth();
        this.sportObject = swimmingPool.getSportObject();
    }
}
