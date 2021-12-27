package com.example.signupservice.controller;

import com.example.signupservice.dto.SignUpDto;
import com.example.signupservice.entity.LessonsEntity;
import com.example.signupservice.service.SignUpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService service;

    @PostMapping
    public String signUp(@RequestHeader("Authorization") String auth, @RequestBody SignUpDto dto){
        return service.signUp(dto, auth);
    }

    @GetMapping("/viewLessons")
    public List<LessonsEntity> viewLessons(@RequestHeader("Authorization") String auth){
        return service.viewLessons(auth);
    }
}
