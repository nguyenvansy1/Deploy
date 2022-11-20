package com.example.becapstone1.repository;

import com.example.becapstone1.model.user.Majors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMajorsRepository extends JpaRepository<Majors,Integer> {
}
