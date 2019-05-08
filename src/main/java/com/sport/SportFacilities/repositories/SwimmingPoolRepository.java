package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.SwimmingPool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SwimmingPoolRepository extends CrudRepository<Integer, SwimmingPool> {
    
    //Todo o danej liczbie torów
    Optional<Set<SwimmingPool>> findAllByLanesQuantity(Integer lanesQuantity);

    //Todo o danej głębokości
    Optional<Set<SwimmingPool>> findAllByDepth(Float depth);
}
