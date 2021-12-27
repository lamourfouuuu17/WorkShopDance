package com.example.shopservice.repository;

import com.example.shopservice.entity.GoodsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShopRepository extends CrudRepository<GoodsEntity, Long> {
    public List<GoodsEntity> findAll();
}
