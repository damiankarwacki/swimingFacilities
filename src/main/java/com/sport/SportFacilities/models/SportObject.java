package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class SportObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Pattern(regexp = "^[A-Z0-9][A-Za-z0-9|\\s]*", message = "{validation.properName}")
    @NonNull
    @Getter
    @Setter
    private String name;
    @OneToMany(mappedBy = "sportObject", fetch = FetchType.LAZY)
    private Set<SwimmingPool> swimmingPool;

    @Valid
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


