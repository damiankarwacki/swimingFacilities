package com.sport.SportFacilities.models;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class Instructor {
    
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private SwimmingPool swimmingPool;

    @OneToOne(mappedBy = "instructor")
    private LessonDetail lessonDetail;
}
