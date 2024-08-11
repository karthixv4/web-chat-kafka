package com.example.demo.websocket;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        // Extract chat room ID from query parameters
        try {
            // Assuming 'request' is an instance of HttpMessage
            HttpHeaders headers = request.getHeaders();
            String chatRoomId = headers.getFirst("chat-room-id"); // Use getFirst to retrieve the header value
        
            if (chatRoomId != null) {
                System.out.println("Inside before handshake: "+chatRoomId);
                attributes.put("chatRoomId", chatRoomId);
            }
            return true;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'beforeHandshake'");
        }

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            @Nullable Exception exception) {
        // TODO Auto-generated method stub

    }

}
