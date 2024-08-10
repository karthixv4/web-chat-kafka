package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data.GroupRepository;
import com.example.demo.data.MessageRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.kafka.ChatMessageProducer;
import com.example.demo.model.Group;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import java.util.Date;

@Service
public class ChatService {

    
    @Autowired
    private ChatMessageProducer chatMessageProducer; 
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MessageRepository messageRepository;

    public void sendMessage(Long userId, Long groupId, String content) {
        Message message = new Message();
        message.setContent(content);
        message.setSenderId(userId);
        message.setGroupId(groupId);
        message.setCreatedAt(new Date());

        // Save the message to the database
        messageRepository.save(message);

        // Send the message to Kafka
        chatMessageProducer.sendMessage(message);
    }

    public List<Message> getMessagesForGroup(Long groupId) {
        return messageRepository.findByGroupId(groupId);
    }

    public User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        return userRepository.save(user);
    }

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

    public Set<Group> getUserGroups(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getGroups();
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
}