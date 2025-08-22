package com.example.kafka_test.controllers;
import com.example.kafka_test.models.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {
	@MessageMapping("/chat/{groupId}")
	@SendTo("/topic/{groupId}")
	public ChatMessage send(@DestinationVariable String groupId, @Payload ChatMessage message) {
		return message; // broadcast message to group
	}

}
