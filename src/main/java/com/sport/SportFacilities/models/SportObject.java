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
    @Setter
    private Integer id;

    @NonNull
    @Getter
    @Setter
    private String name;
    @OneToMany(mappedBy = "sportObject", fetch = FetchType.LAZY)
    private Set<SwimmingPool> swimmingPool;

    @NonNull
    @Setter
    @Getter
    @OneToOne(cascade = CascadeType.ALL,
            optional = false, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    public SportObject(Integer id, SportObject sportObject) {
        this.id = id;
        this.address = sportObject.getAddress();
    }
}
