package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {

}
