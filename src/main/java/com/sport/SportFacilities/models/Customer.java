package com.sport.SportFacilities.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "customer_lessons",
                  joinColumns = @JoinColumn(name = "customer_id"),
                  inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<Lesson> lessons;
}
