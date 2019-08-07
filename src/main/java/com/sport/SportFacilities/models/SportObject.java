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

    @Getter
    @Setter
    @OneToMany(mappedBy = "sportObject", fetch = FetchType.LAZY)
    private Set<SwimmingPool> swimmingPools;

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
        this.name = sportObject.getName();
        this.swimmingPools = sportObject.getSwimmingPools();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SportObject)) return false;

        SportObject that = (SportObject) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!name.equals(that.name)) return false;
        if (swimmingPools != null ? !swimmingPools.equals(that.swimmingPools) : that.swimmingPools != null)
            return false;
        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + (swimmingPools != null ? swimmingPools.hashCode() : 0);
        result = 31 * result + address.hashCode();
        return result;
    }
}


