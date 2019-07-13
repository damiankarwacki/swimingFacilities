package com.sport.SportFacilities.controllers;

import com.sport.SportFacilities.models.SportObject;
import com.sport.SportFacilities.services.SportObjectService;
import com.sport.SportFacilities.utils.HateoasUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/sport-objects")
@AllArgsConstructor
public class SportObjectController {

    private SportObjectService sportObjectService;
    private HateoasUtils hateoasUtils;

    @Autowired
    public SportObjectController(SportObjectService sportObjectService) {
        this.sportObjectService = sportObjectService;
        this.hateoasUtils = new HateoasUtils();
    }

    @GetMapping()
    public ResponseEntity getAllSportObjects() {
        Set<SportObject> sportObjects = sportObjectService.getAllSportObjects();
        return ResponseEntity.ok(sportObjects);
    }

    @GetMapping(params = {"city"})
    public ResponseEntity getAllSportObjectFromCity(@RequestParam String city) throws Exception {
        Set<SportObject> sportObjects = sportObjectService.getAllSportObjectsByCity(city);
        return ResponseEntity.ok(sportObjects);
    }

    @GetMapping("/{id}")
    public ResponseEntity getSportObjectById(@PathVariable Integer id) {
        SportObject sportObject = sportObjectService.getSportObjectById(id);
        // Resource umożliwia dodawanie linków
        Resource<SportObject> resource = new Resource<>(sportObject);
        // Dla przykładu:
        // Do zwracanego modelu dodany jest też link umożliwiający pobranie wszystkich istniejących obiektów
        // Link wygenerowany jest na podstawie metody getAllSportObjects i nazwany all-sport-objects
        Link linkForGettingAllSportObjects = linkTo(methodOn(SportObjectController.class).getAllSportObjects())
                .withRel("all-sport-objects");
        resource.add(linkForGettingAllSportObjects);
        return ResponseEntity.ok(resource);
    }

    @PostMapping()
    public ResponseEntity createSportObject(@Valid @RequestBody SportObject sportObject) {
        SportObject createdSportObject = sportObjectService.createNewSportObject(sportObject);
        URI uri = hateoasUtils.getUriWithPathAndParams("/{id}", createdSportObject.getId());
        return ResponseEntity.created(uri).body(createdSportObject);
    }

    @PutMapping()
    public ResponseEntity editSportObject(@Valid @RequestBody SportObject sportObject) {
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
