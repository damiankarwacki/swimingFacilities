package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @NonNull
    private LocalDate orderDate;
    
    @ManyToMany(mappedBy = "lessons")
    private Set<Customer> customers;
    
    @OneToOne(mappedBy = "lesson",
                  cascade = CascadeType.ALL,
                  fetch = FetchType.LAZY,
                  optional = false,
                  orphanRemoval = true)
    private LessonDetail lessonDetail;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "swimming_pool_id")
    private SwimmingPool swimmingPool;
}
