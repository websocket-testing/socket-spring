package com.example.kafka_test.exceptions;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String clientMessage = message.getPayload();
        System.out.println("Received: " + clientMessage);

        // Echo back to client
        session.sendMessage(new TextMessage("Server received: " + clientMessage));
    }
}
