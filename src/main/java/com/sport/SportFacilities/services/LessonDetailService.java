package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.models.LessonDetail;
import com.sport.SportFacilities.models.LessonType;
import com.sport.SportFacilities.repositories.LessonDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class LessonDetailService {

    LessonDetailRepository lessonDetailRepository;

    @Autowired
    public LessonDetailService(LessonDetailRepository lessonDetailRepository) {
        this.lessonDetailRepository = lessonDetailRepository;
    }

//todo [OMOWIĆ Z DAMIANEM]

//    czy w przypadku gdyby nie udało się stworzyć z jakiegoś powodu nie powinien rzucić wyjątkiem?
    public LessonDetail createLessonDetail(LessonDetail lessonDetail){
        return lessonDetailRepository.save(lessonDetail);
    }

    public Set<LessonDetail> getAllLessonDetails(){
        return Sets.newHashSet(lessonDetailRepository.findAll());
    }

    public LessonDetail getLessonDetailById(Integer id){
        return lessonDetailRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Set<LessonDetail> getAllLessonDetailsByPrice(Float price){
        return lessonDetailRepository.findAllByPrice(price).orElse(Collections.emptySet());
    }

    public Set<LessonDetail> getAllLessonDetailsByPriceBetween(Float price1, Float price2){
        return lessonDetailRepository.findAllByPriceBetween(price1, price2).orElse(Collections.emptySet());
    }

    public Set<LessonDetail> getAllByLessonType(LessonType lessonType){
        return lessonDetailRepository.findAllByLessonType(lessonType).orElse(Collections.emptySet());
    }

    public LessonDetail editLessonDetail(LessonDetail lessonDetail){
        return lessonDetailRepository.save(lessonDetail);
    }

    public void deleteLessonDetail(LessonDetail lessonDetail){
        lessonDetailRepository.delete(lessonDetail);
    }

    public void deleteLessonDetailById(Integer id){
        lessonDetailRepository.deleteById(id);
    }

}
