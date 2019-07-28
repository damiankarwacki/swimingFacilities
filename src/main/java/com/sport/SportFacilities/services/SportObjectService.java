package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.exceptions.SportObjectNotFoundException;
import com.sport.SportFacilities.models.SportObject;
import com.sport.SportFacilities.repositories.SportObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

//TODO dopisać testy do obsługi wyjątków
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
        return sportObjectRepository.findById(id)
                .orElseThrow(() -> new SportObjectNotFoundException("id",String.valueOf(id)));
    }

    public Set<SportObject> getAllSportObjects(){
        return Sets.newHashSet(sportObjectRepository.findAll());
    }

    public Set<SportObject> getAllSportObjectsByCity(String city){
        return sportObjectRepository.findAllByCity(city)
                .orElseThrow(() -> new SportObjectNotFoundException("city",city));
    }

    public SportObject editSportObject(Integer id, SportObject editedSportObject){
        editedSportObject.setId(id);
        return sportObjectRepository.save(editedSportObject);
    }
    
    public void deleteSportObject(SportObject sportObject){
        sportObjectRepository.delete(sportObject);
    }
    
}
