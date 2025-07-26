package abdumalik.prodev.notificationservice.service;

import abdumalik.prodev.notificationservice.model.Notification;
import abdumalik.prodev.notificationservice.model.entity.NotificationType;
import abdumalik.prodev.notificationservice.repository.NotificationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepo notificationRepository;
    private SimpMessagingTemplate messagingTemplate;

    public void createAndSendNotification(UUID recipientId, UUID senderId, String senderUsername, NotificationType type, UUID entityId) {
        if (recipientId.equals(senderId)) {
            log.info("Skipping self-notification for user {}", recipientId);
            return;
        }

        Notification notification = new Notification();
        notification.setRecipientId(recipientId);
        notification.setSenderId(senderId);
        notification.setSenderUsername(senderUsername);
        notification.setType(type);
        notification.setEntityId(entityId);
        notification.setMessage(buildMessage(senderUsername, type));

        Notification savedNotification = notificationRepository.save(notification);
        log.info("Saved notification {} for user {}", savedNotification.getId(), recipientId);

        // Push the notification to the user via WebSocket
        // The topic is user-specific, e.g., "/topic/notifications/123"
        String userDestination = "/topic/notifications/" + recipientId;
        messagingTemplate.convertAndSend(userDestination, savedNotification);
        log.info("Sent real-time notification to destination: {}", userDestination);
    }

    private String buildMessage(String senderUsername, NotificationType type) {
        return switch (type) {
            case NEW_FOLLOWER -> senderUsername + " started following you.";
            case POST_LIKE -> senderUsername + " liked your post.";
            case POST_COMMENT -> senderUsername + " commented on your post.";
        };
    }

}