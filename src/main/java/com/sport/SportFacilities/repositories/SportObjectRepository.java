package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.Address;
import com.sport.SportFacilities.models.SportObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SportObjectRepository extends CrudRepository<SportObject, Integer> {
    
    
    //Tip: Hibernate domyślnie robi join po polu oznaczonym @JoinColumn wewnątrz encji
//    @Query("SELECT o FROM SportObject o JOIN  Address a ON a.id = o.address WHERE a.city = :city")
    @Query("SELECT o FROM SportObject o, Address a WHERE a.id = o.address AND  a.city = :city")
    Optional<Set<SportObject>> findAllByCity(@Param("city") String city);
    
}
