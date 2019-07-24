package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class SwimmingPool {
    
    @Id
    @Getter
    @Setter
    @GeneratedValue
    private Integer id;
    
    @Getter
    @NonNull
    @Positive
    private Integer lanesQuantity;
    
    @Getter
    @NonNull
    @Positive
    private Float lenght;
    
    @Getter
    @NonNull
    @Positive
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
