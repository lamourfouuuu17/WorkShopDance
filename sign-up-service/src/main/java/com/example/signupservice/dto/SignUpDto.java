package com.example.signupservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class SignUpDto {
    private String teacherEmail;
    private LocalDateTime date;
}
