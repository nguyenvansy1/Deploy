package com.example.becapstone1.service.Impl;

import com.example.becapstone1.model.user.Class;
import com.example.becapstone1.repository.IClassRepository;
import com.example.becapstone1.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService implements IClassService {
    @Autowired
    private IClassRepository classRepository;
    @Override
    public List<Class> findAllClass() {
        return classRepository.findAll();
    }
}
