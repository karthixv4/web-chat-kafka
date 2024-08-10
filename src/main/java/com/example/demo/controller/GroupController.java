package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Group;
import com.example.demo.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group createdGroup = groupService.createGroup(group.getName(), group.getCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinGroup(@RequestParam Long userId, @RequestParam Long groupId) {
        groupService.joinGroup(userId, groupId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leave")
    public ResponseEntity<Void> leaveGroup(@RequestParam Long userId, @RequestParam Long groupId) {
        groupService.removeUserFromGroup(userId, groupId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok().build();
    }
}
