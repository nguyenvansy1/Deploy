package com.example.becapstone1.service.Impl;

import com.example.becapstone1.model.user.Majors;
import com.example.becapstone1.repository.IMajorsRepository;
import com.example.becapstone1.service.IMajorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorsService implements IMajorsService {

    @Autowired
    private IMajorsRepository iMajorsRepository;

    @Override
    public List<Majors> findAllMajors() {
        return iMajorsRepository.findAll();
    }
}
