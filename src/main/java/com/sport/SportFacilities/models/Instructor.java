package com.sport.SportFacilities.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Instructor {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private SwimmingPool swimmingPool;
    
    @OneToMany(mappedBy = "instructor",
                  fetch = FetchType.LAZY)
    private Set<Lesson> lessons;
}
