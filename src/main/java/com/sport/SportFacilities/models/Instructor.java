package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Instructor {
    
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,}")
    @NonNull
    @Getter
    @Setter
    private String name;

    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,}")
    @NonNull
    @Getter
    @Setter
    private String surname;

    @Pattern(regexp = "^[+][0-9 ]{1,}")
    @NonNull
    @Getter
    @Setter
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
