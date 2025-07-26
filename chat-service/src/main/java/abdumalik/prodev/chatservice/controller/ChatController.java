package abdumalik.prodev.chatservice.controller;

import abdumalik.prodev.chatservice.model.ChatMessage;
import abdumalik.prodev.chatservice.model.ChatRoom;
import abdumalik.prodev.chatservice.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "APIs and WebSocket endpoints for Direct Messaging")
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        Principal principal = Objects.requireNonNull(headerAccessor.getUser());

        UUID senderId = UUID.fromString(principal.getName());

        // Ensure the senderId from the payload matches the authenticated principal
        chatMessage.setSenderId(senderId);

        chatService.sendMessage(chatMessage);
    }

    @GetMapping("/history")
    public ResponseEntity<Page<ChatMessage>> getChatHistory(
            @RequestHeader("X-Authenticated-User-Id") UUID userId,
            @RequestParam UUID recipientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(chatService.getChatHistory(userId, recipientId, page, size));
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoom>> getRecentChatRooms(
            @RequestHeader("X-Authenticated-User-Id") UUID userId) {
        return ResponseEntity.ok(chatService.getRecentChats(userId));
    }

}