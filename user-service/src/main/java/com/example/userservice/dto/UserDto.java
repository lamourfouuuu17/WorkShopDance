package com.example.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
}
