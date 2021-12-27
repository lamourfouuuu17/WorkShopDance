package com.example.userservice.service;

import com.example.userservice.dto.LogInDto;
import com.example.userservice.dto.Role;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.properties.JwtProperties;
import com.example.userservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {
    final JwtProperties jwtProperties;
    final UserRepository repository;

    public String addUser(UserDto userDto) {

        if (repository.getByEmail(userDto.getEmail()) == null) {
            repository.save(toEntity(userDto));
            return "User saved succesfully!";
        } else return "User with this email is already exists!";
    }

    public List<UserDto> getAllUsers() {
        return toDto(StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList()));
    }

    public String logIn(LogInDto dto) {
        try {
            UserEntity user = repository.getByEmail(dto.getEmail());
            if (user.getPassword().equals(dto.getPassword())) {
                return generateToken(user);
            } else {
                return "Wrong credentials";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<String> getAdminEmails() {
        List<UserEntity> admins = repository.findUserEntitiesByRole(Role.ADMIN);
        return admins.stream()
                .map(UserEntity::getEmail)
                .collect(Collectors.toList());
    }

    private UserEntity toEntity(UserDto dto) {
        return UserEntity.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    private List<UserDto> toDto(List<UserEntity> entityList) {
        return entityList.stream()
                .map(entity -> {
                    return UserDto.builder()
                            .name(entity.getName())
                            .surname(entity.getSurname())
                            .role(entity.getRole())
                            .email(entity.getEmail())
                            .id(entity.getId())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private String generateToken(UserEntity user) {
        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("surname", user.getSurname());
        claims.put("role", user.getRole());

        String key = jwtProperties.getSigningKey();
        return Jwts.builder().setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuer("DanceWorkShop")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(1800L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

}
