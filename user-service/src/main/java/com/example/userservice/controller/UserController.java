package com.example.userservice.controller;

import com.example.userservice.dto.LogInDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping("/add")
    public String addUser(@RequestBody UserDto dto) {
        return service.addUser(dto);
    }

    @GetMapping("/list")
    public List<UserDto> getUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/login")
    public String logIn(@RequestBody LogInDto dto){
        return service.logIn(dto);
    }

    @GetMapping("/admin-list")
    public List<String> getAdminEmails(){
        return service.getAdminEmails();
    }
}
