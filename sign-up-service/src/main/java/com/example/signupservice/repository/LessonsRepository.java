package com.example.signupservice.repository;

import com.example.signupservice.entity.LessonsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonsRepository extends CrudRepository<LessonsEntity, Long> {
    public List<LessonsEntity> findAll();
    public List<LessonsEntity> findLessonsEntitiesByStudentEmail(String email);
}
