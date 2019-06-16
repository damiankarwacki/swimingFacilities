package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.Instructor;
import com.sport.SportFacilities.models.LessonDetail;
import com.sport.SportFacilities.models.LessonType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface LessonDetailRepository extends CrudRepository<LessonDetail, Integer> {

//    TODO Krzychu testy

//    todo[OMÓWIĆ Z DAMIANEM]

//    ta metoda nie miała sensu bo przecież może być więcej niż jedna lekcja w danej cenie
    Optional<Set<LessonDetail>> findAllByPrice(Float price);
//todo [OMÓWIĆ Z DAMIANEM]

//    dodałem pricebetween bo przecież raczej będziemy szukali po widełkach cenowych + zmieniłem na Set bo przecież nie wyjmiemy pojedynczego rekordu
    Optional<Set<LessonDetail>> findAllByPriceBetween(Float price1, Float price2);
    Optional<Set<LessonDetail>> findAllByLessonType(LessonType lessonType);
    
}
