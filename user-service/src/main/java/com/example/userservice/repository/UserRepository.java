package com.example.userservice.repository;

import com.example.userservice.dto.Role;
import com.example.userservice.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    public UserEntity getByEmail(String email);
    public List<UserEntity>findUserEntitiesByRole(Role role);

}
