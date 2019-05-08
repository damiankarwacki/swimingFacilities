package com.sport.SportFacilities.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Lesson {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    private LocalDate orderDate;
    
    @ManyToMany(mappedBy = "lessons")
    private Set<Customer> customers;
    
    @OneToOne(mappedBy = "lesson",
                  cascade = CascadeType.ALL,
                  fetch = FetchType.LAZY,
                  optional = false,
                  orphanRemoval = true)
    private LessonDetail lessonDetail;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimming_pool_id")
    private SwimmingPool swimmingPool;
}
