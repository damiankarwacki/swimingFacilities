package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Set;
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,}")
    @NonNull
    @Getter
    private String name;

    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,}")
    @NonNull
    @Getter
    private String surname;

    @Pattern(regexp = "^[+][0-9 ]{1,}")
    @NonNull
    @Getter
    private String phone;

    @Email
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
