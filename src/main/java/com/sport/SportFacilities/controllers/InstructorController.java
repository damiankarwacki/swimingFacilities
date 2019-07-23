package com.sport.SportFacilities.controllers;

import com.sport.SportFacilities.models.Instructor;
import com.sport.SportFacilities.services.InstructorService;
import com.sport.SportFacilities.utils.HateoasUtils;
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
@RequestMapping("/instructor")
public class InstructorController {

    private InstructorService instructorService;
    private HateoasUtils hateoasUtils;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
        this.hateoasUtils = new HateoasUtils();
    }

    @PostMapping()
    public ResponseEntity createInstructor(@Valid @RequestBody Instructor instructor){
        Instructor createdInstructor = instructorService.createInstructor(instructor);
        URI uri = hateoasUtils.getUriWithPathAndParams("/{id}", createdInstructor.getId());
        return ResponseEntity.created(uri).body(createdInstructor);
    }

    @GetMapping()
    public ResponseEntity getAllInstructors(){
        Set<Instructor> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/{id}")
    public ResponseEntity getInstructorById(@PathVariable Integer id){
        Instructor instructor = instructorService.getInstructorById(id);
        Resource<Instructor> resource = new Resource<>(instructor);
        Link linkForGettingAllInstructors = linkTo(methodOn(InstructorController.class).getAllInstructors()).withRel("all-instructors");
        resource.add(linkForGettingAllInstructors);
        return ResponseEntity.ok(resource);
    }

    @PutMapping()
    public ResponseEntity editInstructorById(@Valid @RequestBody Instructor instructor){
        Instructor editedInstructor = instructorService.editInstructor(instructor);
        URI uri = hateoasUtils.getUriWithPathAndParams("/{id}", editedInstructor.getId());
        return ResponseEntity.created(uri).body(instructor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteInstructor(@PathVariable Integer id){
        Instructor instructorToDelete = instructorService.getInstructorById(id);
        instructorService.deleteInstructor(instructorToDelete);
        return ResponseEntity.ok().build();
    }

}
