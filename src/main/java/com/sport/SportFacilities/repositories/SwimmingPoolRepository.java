package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.SwimmingPool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SwimmingPoolRepository extends CrudRepository<SwimmingPool, Integer> {

    Optional<Set<SwimmingPool>> findAllByLanesQuantity(Integer lanesQuantity);

    Optional<Set<SwimmingPool>> findAllByDepth(Float depth);
}
