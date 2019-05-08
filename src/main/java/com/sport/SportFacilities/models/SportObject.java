package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class SportObject {
    
    @Id
    @GeneratedValue
    @Getter
    private Integer id;
    
    @OneToMany(mappedBy = "sportObject", fetch = FetchType.LAZY)
    private Set<SwimmingPool> swimmingPool;

    @NonNull
    @Setter
    @Getter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
                  optional = false,
                  orphanRemoval = true)
    private Address address;

    @NonNull
    private String name;
}
