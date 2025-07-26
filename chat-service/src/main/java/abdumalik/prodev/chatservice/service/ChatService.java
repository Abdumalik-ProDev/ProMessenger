package abdumalik.prodev.chatservice.service;

import abdumalik.prodev.chatservice.model.ChatMessage;
import abdumalik.prodev.chatservice.model.ChatRoom;
import abdumalik.prodev.chatservice.repository.ChatMessageRepo;
import abdumalik.prodev.chatservice.repository.ChatRoomRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ChatService {

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;
    @Autowired
    private ChatMessageRepo chatMessageRepository;
    @Autowired
    private ChatRoomRepo chatRoomRepository;
    @Autowired
    private ChatRoomService chatRoomService;

    @Value("${app.kafka.topic}")
    private String topic;

    public void sendMessage(ChatMessage chatMessage) {
        log.info("Received message from senderId {} to recipientId {}", chatMessage.getSenderId(), chatMessage.getRecipientId());

        // Find or create the chat room and update its last message timestamp
        chatRoomService.findOrCreateChatRoom(chatMessage.getSenderId(), chatMessage.getRecipientId());

        String chatRoomId = chatRoomService.getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId());
        chatMessage.setChatRoomId(chatRoomId);

        kafkaTemplate.send(topic, chatMessage);
        log.info("Published message to Kafka topic: {}", topic);
    }

    public Page<ChatMessage> getChatHistory(UUID user1, UUID user2, int page, int size) {
        String chatRoomId = chatRoomService.getChatRoomId(user1, user2);
        log.info("Fetching chat history for room: {}", chatRoomId);
        Pageable pageable = PageRequest.of(page, size);
        return chatMessageRepository.findByChatRoomIdOrderByTimestampDesc(chatRoomId, pageable);
    }

    public List<ChatRoom> getRecentChats(UUID userId) {
        log.info("Fetching recent chats for user: {}", userId);
        return chatRoomRepository.findByParticipantIdsContainsOrderByLastMessageAtDesc(userId);
    }

}