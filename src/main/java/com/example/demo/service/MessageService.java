package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data.MessageRepository;
import com.example.demo.kafka.ChatMessageProducer;
import com.example.demo.model.Message;

@Service
public class MessageService {
    
    @Autowired
    private ChatMessageProducer chatMessageProducer; 
    
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

    public List<Message> getMessagesByUser(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
