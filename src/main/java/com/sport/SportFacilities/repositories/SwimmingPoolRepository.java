package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.SwimmingPool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwimmingPoolRepository extends CrudRepository<Integer, SwimmingPool> {
    
    //Todo o danej licznie torów
    //Todo o danej głębokości
}
