package com.example.demo.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Group;
import com.example.demo.model.SendMessageRequest;
import com.example.demo.model.User;
import com.example.demo.service.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody String username) {
        User user = chatService.createUser(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group createdGroup = chatService.createGroup(group.getName(), group.getCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PostMapping("/groups/join")
    public ResponseEntity<Void> joinGroup(@RequestParam Long userId, @RequestParam Long groupId) {
        chatService.joinGroup(userId, groupId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}/groups")
    public ResponseEntity<Set<Group>> getUserGroups(@PathVariable Long userId) {
        Set<Group> groups = chatService.getUserGroups(userId);
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody SendMessageRequest request) {
        chatService.sendMessage(request.getSenderId(), request.getGroupId(), request.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/groups/leave")
    public ResponseEntity<Void> leaveGroup(@RequestParam Long userId, @RequestParam Long groupId) {
        chatService.removeUserFromGroup(userId, groupId);
        return ResponseEntity.ok().build();
    }
}