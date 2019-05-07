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
public interface SportObjectRepository extends CrudRepository<Integer, SportObject> {
    
    //Todo po adresie
    @Query("SELECT o FROM SportObject o JOIN Address a ON o.address = a.id WHERE a.street = :street AND a.city = :city AND a.postCode = :postCode")
    Optional<Set<SportObject>> findAllByAddress(@Param("street") String street, @Param("city") String city, @Param("postCode") String postCode);


//    A czy nie można w ten sposób?:
//    Optional<Set<SportObject>> findAllByAddress(Address address);

    //Todo po mieście
    @Query("SELECT o FROM SportObject o JOIN Address a ON o.address = a.id WHERE a.city = :city")
    Optional<Set<SportObject>> findAllByCity(@Param("city") String city);


    //Todo zrób ten sam SELECT pomijając JOIN
//    @Query("SELECT o FROM SportObject o, Address a WHERE o.address = a.id AND a.city = :city")
//    Optional<Set<SportObject>> findAllByCity(@Param("city") String city);

//    Optional<Set<SportObject>> findAllByCity(String city);

    
}
