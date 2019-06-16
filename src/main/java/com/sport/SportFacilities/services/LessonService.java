package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.models.Instructor;
import com.sport.SportFacilities.models.Lesson;
import com.sport.SportFacilities.models.LessonType;
import com.sport.SportFacilities.models.SportObject;
import com.sport.SportFacilities.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class LessonService {

    private LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
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
        return lessonRepository.findAllByLessonDetailLessonType(lessonType).orElse(Collections.emptySet());
    }

    public Set<Lesson> getAllLessonsByOrderDate(LocalDate orderDate){
        return lessonRepository.findAllByOrderDate(orderDate).orElse(Collections.emptySet());
    }

    public Set<Lesson> getAllLessonsBySportObject(SportObject sportObject){
        return lessonRepository.findAllBySportObject(sportObject).orElse(Collections.emptySet());
    }

    public Set<Lesson> getAllLessonsByInstructor(Instructor instructor){
        return lessonRepository.findAllByInstructor(instructor).orElse(Collections.emptySet());
    }

    public Lesson editLesson(Lesson lesson){
        return lessonRepository.save(lesson);
    }

    public void deleteLesson(Lesson lesson){
        lessonRepository.delete(lesson);
    }

    public void deleteLessonById(Integer id){
        lessonRepository.deleteById(id);
    }

}
