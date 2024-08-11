package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.data.MessageRepository;
import com.example.demo.model.Message;
import com.example.demo.websocket.ChatWebSocketHandler;

@Service
public class ChatMessageConsumer {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;

    @KafkaListener(topics = "web-chat", groupId = "chat-group")
    public void listen(Message message) {
        // Store the message in the database
        System.out.println("MESSGAEL "+message);
        messageRepository.save(message);

        // Broadcast the message to connected WebSocket clients
        chatWebSocketHandler.broadcastMessage(message.getGroupId(), message, null);
    }
}