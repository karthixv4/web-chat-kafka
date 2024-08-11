package com.example.demo.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.demo.kafka.ChatMessageProducer;
import com.example.demo.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private Map<Long, Set<WebSocketSession>> chatRoomSessions = new HashMap<>();

    @Autowired
    private ChatMessageProducer chatMessageProducer;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Handle new WebSocket connections
        System.out.println("Connection established with session ID: " + session.getId());
        Long chatRoomId = getChatRoomId(session);
        chatRoomSessions.computeIfAbsent(chatRoomId, k -> new HashSet<>())
                        .add(session);
        System.out.println("Session added to chat room ID: " + chatRoomId);
         System.out.println("SessionchatRoomSessions: " + chatRoomSessions);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming WebSocket messages
        System.out.println("Received message: " + message.getPayload());
        System.out.println("Session details: " + session);
        Message chatMessage = parseMessage(message.getPayload());
        Long chatRoomId = getChatRoomId(session);

        // Produce the message to Kafka
        chatMessageProducer.sendMessage(chatMessage);

        // Broadcast the message to other clients in the same chat room
        broadcastMessage(chatRoomId, chatMessage, session);
    }

    // ...

    public void broadcastMessage(Long chatRoomId, Message message, WebSocketSession exclude) {
        // Broadcast the message to all clients in the same chat room, except the sender
        System.out.println("Inside BS chatRoomID: "+chatRoomId+", message: "+message.getContent());
        System.out.println("Full chatRoomSessions map before broadcast: " + chatRoomSessions);
        Set<WebSocketSession> sessions = chatRoomSessions.get(chatRoomId);
        System.out.println("Inside broadcast session: "+sessions);
        if (sessions != null) {
            System.out.println("Inside broadcast  2: "+sessions);
            for (WebSocketSession session : sessions) {
                if (!session.equals(exclude)) {
                    try {
                        session.sendMessage(new TextMessage(message.getContent()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Long getChatRoomId(WebSocketSession session) {
        // Retrieve chat room ID from session attributes
        String chatRoomId = (String) session.getAttributes().get("chatRoomId");
        return chatRoomId != null ? Long.parseLong(chatRoomId) : 1L;
    }
    private Message parseMessage(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(payload, Message.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse message", e);
        }
    }
}