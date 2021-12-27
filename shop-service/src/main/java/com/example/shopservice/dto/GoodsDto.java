package com.example.shopservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodsDto {
    private String name;
    private Double price;
    private String category;
}
