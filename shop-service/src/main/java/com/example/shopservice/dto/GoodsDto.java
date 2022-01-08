package com.example.shopservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GoodsDto {
    private String name;
    private Double price;
    private String category;
    private String description;
    private LocalDate date;
}
