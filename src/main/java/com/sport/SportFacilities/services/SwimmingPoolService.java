package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.exceptions.SwimmingPoolNotFoundException;
import com.sport.SportFacilities.models.SwimmingPool;
import com.sport.SportFacilities.repositories.SwimmingPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;


//TODO Krzychu, stworzyć controller, stworzyć wyjątek, dopisać testy jednostkowe do obługi błedów
@Service
public class SwimmingPoolService {

    private SwimmingPoolRepository swimmingPoolRepository;

    @Autowired
    public SwimmingPoolService(SwimmingPoolRepository swimmingPoolRepository) {
        this.swimmingPoolRepository = swimmingPoolRepository;
    }

    public SwimmingPool createSwimmingPool(SwimmingPool swimmingPool){
        return swimmingPoolRepository.save(swimmingPool);
    }

    public SwimmingPool getSwimmingPoolById(Integer id){
        return swimmingPoolRepository.findById(id).orElseThrow(() -> new SwimmingPoolNotFoundException("id", String.valueOf(id)));
    }

    public Set<SwimmingPool> getAllSwimmingPools(){
        return Sets.newHashSet(swimmingPoolRepository.findAll());
    }

    public Set<SwimmingPool> getAllSwimmingPoolsByDepth(Float depth){
        return swimmingPoolRepository.findAllByDepth(depth).orElse(Collections.emptySet());
    }

    public Set<SwimmingPool> getAllSwimmingPoolsByLanesQuantity(Integer lanes){
        return swimmingPoolRepository.findAllByLanesQuantity(lanes).orElse(Collections.emptySet());
    }

    public SwimmingPool editSwimmingPool(SwimmingPool swimmingPool, Integer id){
        swimmingPool.setId(id);
        return swimmingPoolRepository.save(swimmingPool);
    }

    public void deleteSwimmingPool(SwimmingPool swimmingPool){
        swimmingPoolRepository.delete(swimmingPool);
    }

}
