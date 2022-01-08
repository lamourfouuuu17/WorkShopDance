package com.example.signupservice.service;

import com.example.signupservice.dto.SignUpDto;
import com.example.signupservice.entity.LessonsEntity;
import com.example.signupservice.entity.SignUpEntity;
import com.example.signupservice.repository.LessonsRepository;
import com.example.signupservice.repository.SignUpRepository;
import com.example.userservice.dto.Role;
import com.example.userservice.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SignUpService {
    final SignUpRepository signUpRepository;
    final LessonsRepository lessonsRepository;
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();

    public String signUp(SignUpDto dto, String auth) {
        JsonNode currentUser = null;
        try {
            currentUser = decode(auth);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Wrong token!";
        }
        if (dto.getDate().isBefore(LocalDateTime.now().plus(1, ChronoUnit.DAYS))) {
            return "Please, sign up on at least 24 hours since now!";
        }
        String currentUserEmail = currentUser.get("email").asText();
        String teacherEmail = dto.getTeacherEmail();

        List<UserDto> userList = Arrays.asList(Objects.requireNonNull(restTemplate
                .getForObject("http://localhost:8085/users/list", UserDto[].class)));
        Optional<Long> currentUserId = userList.stream()
                .filter(user -> currentUserEmail.equals(user.getEmail()))
                .map(UserDto::getId).findFirst();
        Optional<Long> teacherId = userList.stream()
                .filter(user -> Role.TEACHER.equals(user.getRole()) && teacherEmail.equals(user.getEmail()))
                .map(UserDto::getId).findFirst();
        if (teacherId.isPresent() && currentUserId.isPresent()) {
            signUpRepository.save(
                    SignUpEntity.builder()
                            .studentId(currentUserId.get())
                            .teacherId(teacherId.get())
                            .date(dto.getDate())
                            .build());
            return "You successfully signed up for a lesson with " + dto.getTeacherEmail() + "on " + dto.getDate();
        } else return "User not found!";
    }

    public List<LessonsEntity> viewLessons(String auth) {
        JsonNode currentUser = null;
        try {
            currentUser = decode(auth);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Role userRole = Role.valueOf(currentUser.get("role").asText());
        if (Role.TEACHER.equals(userRole) || Role.ADMIN.equals(userRole)) {
            return lessonsRepository.findAll();
        } else {
            return lessonsRepository.findLessonsEntitiesByStudentEmail(currentUser.get("email").asText());
        }
    }

    private JsonNode decode(String token) throws JsonProcessingException {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String header = new String(decoder.decode(chunks[1]));

        return mapper.readTree(header);
    }
}
