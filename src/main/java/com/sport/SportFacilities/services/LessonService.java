package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.models.Instructor;
import com.sport.SportFacilities.models.Lesson;
import com.sport.SportFacilities.models.LessonType;
import com.sport.SportFacilities.models.SportObject;
import com.sport.SportFacilities.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

//TODO Damian Karwacki stworzyć controller, stworzyć wyjątek, dopisać testy jednostkowe do obługi błedów
@Service
public class LessonService {

    private LessonRepository lessonRepository;
    private final InstructorService instructorService;

    @Autowired
    public LessonService(LessonRepository lessonRepository, InstructorService instructorService) {
        this.lessonRepository = lessonRepository;
        this.instructorService = instructorService;
    }

    public Lesson createLesson(Lesson lesson){
        return lessonRepository.save(lesson);
    }

    public Lesson getLessonById(Integer id){
        return lessonRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Set<Lesson> getAllLessons(){
        return Sets.newHashSet(lessonRepository.findAll());
    }

    public Set<Lesson> getAllByLessonsByLessonType(LessonType lessonType){
        return lessonRepository.findAllByLessonType(lessonType).orElse(Collections.emptySet());
    }

    public Set<Lesson> getAllLessonsByOrderDate(LocalDate orderDate){
        return lessonRepository.findAllByOrderDate(orderDate).orElse(Collections.emptySet());
    }

    public Set<Lesson> getAllLessonsBySportObject(SportObject sportObject){
        return lessonRepository.findAllBySportObject(sportObject).orElse(Collections.emptySet());
    }

    @Transactional
    public Set<Lesson> getAllLessonsByInstructorId(Integer instructorId){
        Instructor instructor = instructorService.getInstructorById(instructorId);
        return lessonRepository.findAllByInstructor(instructor).orElse(Collections.emptySet());
    }

    public Lesson editLesson(Integer id, Lesson lesson){
        lesson.setId(id);
        return lessonRepository.save(lesson);
    }

    public void deleteLessonById(Integer id){
        lessonRepository.delete(getLessonById(id));
    }

}
