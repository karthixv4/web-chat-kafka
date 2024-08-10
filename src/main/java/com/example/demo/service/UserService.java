package com.example.demo.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data.GroupRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.model.Group;
import com.example.demo.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    public User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        return userRepository.save(user);
    }

    public Set<Group> getUserGroups(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getGroups();
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
}
