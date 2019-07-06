package com.sport.SportFacilities.testUtils

import com.sport.SportFacilities.repositories.LessonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.sql.SQLException

@Component
class TestUtils {
    
    @Autowired
    private final LessonRepository lessonRepository
    
    Boolean databaseIsUp() {
        try {
            lessonRepository.findAll()
        } catch (SQLException ignored){
            return false
        }
        return true
    }
}
