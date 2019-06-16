package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.models.SwimmingPool;
import com.sport.SportFacilities.repositories.SwimmingPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

public class SwimmingPoolService {

    private SwimmingPoolRepository swimmingPoolRepository;

    @Autowired
    public SwimmingPoolService(SwimmingPoolRepository swimmingPoolRepository) {
        this.swimmingPoolRepository = swimmingPoolRepository;
    }

    public SwimmingPool createSwimmingPool(SwimmingPool swimmingPool){
//        Pomyślałem że tu powinno być saveAll() żeby była możliwość zapisania więcej niż jednego na raz nie?
        return swimmingPoolRepository.save(swimmingPool);
    }

    public SwimmingPool getSwimmingPoolById(Integer id){
        return swimmingPoolRepository.findById(id).orElseThrow(NoSuchElementException::new);
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

    public SwimmingPool editSwimmingPool(SwimmingPool swimmingPool){
        return swimmingPoolRepository.save(swimmingPool);
    }

    public void deleteSwimmingPool(SwimmingPool swimmingPool){
        swimmingPoolRepository.delete(swimmingPool);
    }

}
