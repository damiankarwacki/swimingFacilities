package com.sport.SportFacilities.controllers;

import com.google.common.base.Strings;
import com.sport.SportFacilities.models.SportObject;
import com.sport.SportFacilities.services.SportObjectService;
import com.sport.SportFacilities.utils.HateoasHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/sport-objects")
public class SportObjectController {

    @Autowired
    SportObjectService sportObjectService;

    @GetMapping
    public ResponseEntity getSportObjectsWithGivenCondition(@RequestParam(value = "city", required = false) String city) throws Exception {
        Set<SportObject> sportObjects;
        if (Strings.isNullOrEmpty(city)){
            sportObjects = sportObjectService.getAllSportObjects();
        } else {
            sportObjects = sportObjectService.getAllSportObjectsByCity(city);
        }
        return ResponseEntity.ok(sportObjects);
    }

    @GetMapping("/{id}")
    public ResponseEntity getSportObjectById(@PathVariable Integer id) {
        SportObject sportObject = sportObjectService.getSportObjectById(id);
        return ResponseEntity.ok(sportObject);
    }

    @PostMapping()
    public ResponseEntity createSportObject(@RequestBody SportObject sportObject) {
        SportObject createdSportObject = sportObjectService.createNewSportObject(sportObject);
        URI uri = HateoasHelper.getUriWithPathAndParams("/{id}", createdSportObject.getId());
        return ResponseEntity.created(uri).body(createdSportObject);
    }

    @PutMapping()
    public ResponseEntity editSportObject(@RequestBody SportObject sportObject) {
        SportObject updatedSportObject = sportObjectService.editSportObject(sportObject);
        return ResponseEntity.ok(updatedSportObject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSportObject(@PathVariable Integer id) {
        SportObject sportObject = sportObjectService.getSportObjectById(id);
        sportObjectService.deleteSportObject(sportObject);
        return ResponseEntity.ok().build();
    }
}
