package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data.GroupRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.model.Group;
import com.example.demo.model.User;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public Group createGroup(String name, String code) {
        Group group = new Group();
        group.setName(name);
        group.setCode(code);
        return groupRepository.save(group);
    }

    public void joinGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        group.getUsers().add(user);
        user.getGroups().add(group);

        groupRepository.save(group);
        userRepository.save(user);
    }

    public void removeUserFromGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        group.getUsers().remove(user);
        user.getGroups().remove(group);

        groupRepository.save(group);
        userRepository.save(user);
    }

    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        groupRepository.delete(group);
    }

    public Set<User> getUsersByGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        return group.getUsers();
    }
}
