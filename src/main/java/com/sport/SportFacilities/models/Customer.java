package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
//TODO Krzychu, walidacja pól
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {
    
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
    @NonNull
    @Getter
    private String email;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "customer_lessons",
                  joinColumns = @JoinColumn(name = "customer_id"),
                  inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<Lesson> lessons;
    
    public Customer(Integer id, Customer customer) {
        this.id = id;
        this.name = customer.getName();
        this.surname = customer.getSurname();
        this.phone = customer.getPhone();
        this.email = customer.getEmail();
    }
}
