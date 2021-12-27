package com.example.shopservice.controller;

import com.example.shopservice.dto.GoodsDto;
import com.example.shopservice.entity.GoodsEntity;
import com.example.shopservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ShopService service;
    // token of admin
    @PostMapping("/add")
    public String addGoods(@RequestHeader(name = "authorization") String auth, @RequestBody GoodsDto dto) {
        return service.addGoods(auth, dto);
    }

    @GetMapping
    public List<GoodsDto> getAll(){
        return service.getAll();
    }
}
