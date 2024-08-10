package com.example.demo.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByCode(String code);
}