package com.sport.SportFacilities.controllers;

import com.sport.SportFacilities.models.Instructor;
import com.sport.SportFacilities.models.Lesson;
import com.sport.SportFacilities.models.LessonType;
import com.sport.SportFacilities.models.SportObject;
import com.sport.SportFacilities.services.InstructorService;
import com.sport.SportFacilities.services.LessonService;
import com.sport.SportFacilities.services.SportObjectService;
import com.sport.SportFacilities.utils.HateoasUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/lessons")
@AllArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final SportObjectService sportObjectService;
    private final HateoasUtils hateoasUtils;

    //TODO przeniesc wyszstkie wstrzykniecia do serwisu
    @Autowired
    public LessonController(LessonService lessonService, SportObjectService sportObjectService) {
        this.lessonService = lessonService;
        this.sportObjectService = sportObjectService;
        this.hateoasUtils = new HateoasUtils();
    }

    @GetMapping()
    public ResponseEntity getAllLessons() {
        Set<Lesson> lessons = lessonService.getAllLessons();
        return ResponseEntity.ok(lessons);
    }

    @GetMapping(params = {"lessonType"})
    public ResponseEntity getAllLessonsByLessonType(@RequestParam String lessonType) {
        Set<Lesson> lessons = lessonService.getAllByLessonsByLessonType(LessonType.fromString(lessonType));
        return ResponseEntity.ok(lessons);
    }

    @GetMapping(params = {"instructorId"})
    public ResponseEntity getAllLessonsByInstructorId(@RequestParam Integer instructorId) {
        Set<Lesson> lessons = lessonService.getAllLessonsByInstructorId(instructorId);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping(params = {"orderDate"})
    public ResponseEntity getAllLessonsByOrderDate(@RequestParam String orderDate) {
        Set<Lesson> lessons = lessonService.getAllLessonsByOrderDate(LocalDate.parse(orderDate));
        return ResponseEntity.ok(lessons);
    }

    @GetMapping(params = {"SportObjectId"})
    public ResponseEntity getAllLessonsBySportObjectId(@RequestParam Integer sportObjectId) {
        SportObject sportObject = sportObjectService.getSportObjectById(sportObjectId);
        Set<Lesson> lessons = lessonService.getAllLessonsBySportObject(sportObject);
        return ResponseEntity.ok(lessons);
    }


    @GetMapping("/{id}")
    public ResponseEntity getLessonById(@PathVariable Integer id) {
        Lesson lesson = lessonService.getLessonById(id);
        Resource<Lesson> resource = new Resource<>(lesson);
        Link linkForGettingAllLessons = linkTo(methodOn(LessonController.class).getAllLessons())
                .withRel("all-lessons");
        resource.add(linkForGettingAllLessons);
        return ResponseEntity.ok(resource);
    }

    @PostMapping()
    public ResponseEntity createLesson(@Valid @RequestBody Lesson lesson) {
        Lesson createdLesson = lessonService.createLesson(lesson);
        URI uri = hateoasUtils.getUriWithPathAndParams("/{id}", createdLesson.getId());
        Resource<Lesson> resource = new Resource<>(createdLesson);
        Link linkToUpdate = linkTo(methodOn(LessonController.class).editLesson(createdLesson.getId(),createdLesson))
                .withRel("edit-current-object");
        resource.add(linkToUpdate);
        return ResponseEntity.created(uri).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity editLesson(@PathVariable Integer id, @Valid @RequestBody Lesson lesson) {
        Lesson updatedLesson = lessonService.editLesson(id, lesson);
        URI uri = hateoasUtils.getUriWithPathAndParams("/{id}", id);
        return ResponseEntity.status(HttpStatus.OK).location(uri).body(updatedLesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLesson(@PathVariable Integer id) {
        lessonService.deleteLessonById(id);
        return ResponseEntity.ok().build();
    }
}
