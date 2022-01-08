package com.example.shopservice.service;

import com.example.shopservice.dto.GoodsDto;
import com.example.shopservice.entity.GoodsEntity;
import com.example.shopservice.repository.ShopRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate template = new RestTemplate();
    private final ShopRepository repository;

    public String addGoods(String auth, GoodsDto dto) {
        JsonNode decodedToken;
        try {
            decodedToken = decode(auth);
        } catch (JsonProcessingException e) {
            return "Wrong token!";
        }
        String email = decodedToken.get("email").asText();
        List<String> adminEmails = getAdminEmails();
        if (adminEmails.contains(email)) {
            try {
                repository.save(GoodsEntity.builder()
                        .name(dto.getName())
                        .category(dto.getCategory())
                        .price(dto.getPrice())
                        .description(dto.getDescription())
                        .date(dto.getDate())
                        .build());
                return "Goods saved succesfully!";
            } catch (Exception e) {
                return "Something went wrong! " + e.getMessage();
            }
        } else {
            return "Only admin can add the goods in the shop!";
        }
    }

    public List<GoodsDto> getAll() {
        return toDto(repository.findAll());
    }

    private List<GoodsDto> toDto(List<GoodsEntity> entities) {
        return entities.stream()
                .map(entity -> {
                    return GoodsDto.builder()
                            .name(entity.getName())
                            .category(entity.getCategory())
                            .price(entity.getPrice())
                            .description(entity.getDescription())
                            .date(entity.getDate())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private List<String> getAdminEmails() {
        return template.getForObject("http://localhost:8085/users/admin-list", List.class);
    }


    private JsonNode decode(String token) throws JsonProcessingException {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String header = new String(decoder.decode(chunks[1]));

        return mapper.readTree(header);
    }
}
