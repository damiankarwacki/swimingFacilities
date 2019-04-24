package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.SportObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SportObjectRepository extends CrudRepository<Integer, SportObject> {
    
    //Todo po adresie
    //Todo po mieście
    @Query("SELECT o FROM SportObject o JOIN Address a BY o.address = a.id WHERE a.city = :city")
    Optional<SportObject> findAllByCity(@Param("city") String city);
    //Todo zrób ten sam SELECT pomijając JOIN
    
}
