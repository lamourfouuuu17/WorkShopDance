package com.example.signupservice.repository;

import com.example.signupservice.entity.SignUpEntity;
import com.example.signupservice.service.SignUpService;
import org.springframework.data.repository.CrudRepository;

public interface SignUpRepository extends CrudRepository<SignUpEntity, Long> {
}
