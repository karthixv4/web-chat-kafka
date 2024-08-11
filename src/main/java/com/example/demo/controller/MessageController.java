package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Message;
import com.example.demo.model.SendMessageRequest;
import com.example.demo.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody SendMessageRequest request) {
        messageService.sendMessage(request.getSenderId(), request.getGroupId(), request.getContent());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<Message>> getMessagesForGroup(@PathVariable Long groupId) {
        List<Message> messages = messageService.getMessagesForGroup(groupId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/users/{senderId}")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Long senderId) {
        List<Message> messages = messageService.getMessagesByUser(senderId);
        return ResponseEntity.ok(messages);
    }

    
}
