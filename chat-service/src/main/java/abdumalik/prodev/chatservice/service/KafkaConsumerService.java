package abdumalik.prodev.chatservice.service;

import abdumalik.prodev.chatservice.model.ChatMessage;
import abdumalik.prodev.chatservice.model.entity.MessageStatus;
import abdumalik.prodev.chatservice.repository.ChatMessageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final ChatMessageRepo chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "chat-group")
    public void consumeChatMessage(ChatMessage message) {
        log.info("Consumed chat message for recipient: {}", message.getRecipientId());

        message.setStatus(MessageStatus.DELIVERED);
        ChatMessage savedMessage = chatMessageRepository.save(message);
        log.info("Persisted message with ID: {}", savedMessage.getId());

        messagingTemplate.convertAndSendToUser(
                message.getRecipientId().toString(),
                "/topic/messages",
                savedMessage
        );

        log.info("Pushed message {} to user {}", savedMessage.getId(), savedMessage.getRecipientId());
    }

}