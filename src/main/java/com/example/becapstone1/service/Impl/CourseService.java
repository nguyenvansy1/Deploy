package com.example.becapstone1.service.Impl;

import com.example.becapstone1.model.user.Course;
import com.example.becapstone1.repository.ICourseRepository;
import com.example.becapstone1.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private ICourseRepository iCourseRepository;

    @Override
    public List<Course> findAllCourse() {
        return iCourseRepository.findAll();
    }
}
