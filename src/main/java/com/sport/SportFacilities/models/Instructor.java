package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Instructor {
    
    @Id
    @GeneratedValue
    @Getter
    private Integer id;
    @NonNull
    @Getter
    private String name;
    @NonNull
    @Getter
    private String surname;
    @NonNull
    @Getter
    private String phone;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private SwimmingPool swimmingPool;
    
    @OneToMany(mappedBy = "instructor",
                  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Lesson> lessons;
    
    public Instructor(Integer id, Instructor instructor) {
        this.id = id;
        this.name = instructor.getName();
        this.surname = instructor.getSurname();
        this.phone = instructor.getPhone();
    }
}
