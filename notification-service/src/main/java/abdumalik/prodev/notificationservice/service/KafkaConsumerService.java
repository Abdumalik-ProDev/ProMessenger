package abdumalik.prodev.notificationservice.service;

import abdumalik.prodev.notificationservice.dto.GenericEvent;
import abdumalik.prodev.notificationservice.model.entity.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final NotificationService notificationService;

    // This single listener can consume from multiple topics
    @KafkaListener(topics = {"post.events", "graph.events"}, groupId = "notification-group")
    public void consumeEvents(GenericEvent event) {
        log.info("Consumed event of type: {}", event.getEventType());

        switch (event.getEventType()) {
            case "NEW_FOLLOWER":
                notificationService.createAndSendNotification(
                        event.getTargetUserId(),
                        event.getUserId(),
                        event.getUsername(),
                        NotificationType.NEW_FOLLOWER,
                        null // No specific entity for a follow
                );
                break;
            case "POST_LIKED":
                notificationService.createAndSendNotification(
                        event.getTargetUserId(),
                        event.getUserId(),
                        event.getUsername(),
                        NotificationType.POST_LIKE,
                        event.getEntityId() // The ID of the post that was liked
                );
                break;
            // Add cases for other events like POST_COMMENTED
            default:
                log.warn("Received unhandled event type: {}", event.getEventType());
        }

    }

}