package com.example.demo.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByGroupId(Long groupId);
    List<Message> findBySenderId(Long senderId);
}
