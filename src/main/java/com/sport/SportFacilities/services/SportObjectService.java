package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.models.SportObject;
import com.sport.SportFacilities.repositories.SportObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class SportObjectService {
    
    private final SportObjectRepository sportObjectRepository;
    
    @Autowired
    public SportObjectService(SportObjectRepository sportObjectRepository) {
        this.sportObjectRepository = sportObjectRepository;
    }
    
    public SportObject createNewSportObject(SportObject sportObject){
        return sportObjectRepository.save(sportObject);
    }
    
    public SportObject getSportObjectById(Integer id){
        return sportObjectRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Set<SportObject> getAllSportObjects(){
        return Sets.newHashSet(sportObjectRepository.findAll());
    }
    
    public Set<SportObject> getAllSportObjectsByCity(String city){
        return sportObjectRepository.findAllByCity(city).orElse(Collections.emptySet());
    }
    
    public SportObject editSportObject(SportObject sportObject){
        return sportObjectRepository.save(sportObject);
    }
    
    public void deleteSportObject(SportObject sportObject){
        sportObjectRepository.delete(sportObject);
    }
    
}
