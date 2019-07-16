package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Lesson {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @NonNull
    private LocalDate orderDate;
    
    @ManyToMany(mappedBy = "lessons")
    private Set<Customer> customers;
    
    @OneToOne(cascade = CascadeType.ALL,
              fetch = FetchType.LAZY
//              optional = false, //nie może być 'false' skoro w testach dodajemy obiekty bez tego pola - baza nie chce się postawić
//              orphanRemoval = true
    )
    @JoinColumn(name = "lessondetail_id", referencedColumnName = "id")
    private LessonDetail lessonDetail;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimming_pool_id")
    private SwimmingPool swimmingPool;
}
