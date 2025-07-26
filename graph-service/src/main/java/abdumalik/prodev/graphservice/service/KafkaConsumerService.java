package abdumalik.prodev.graphservice.service;

import abdumalik.prodev.graphservice.dto.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final GraphService graphService;

    @KafkaListener(topics = "${app.kafka.topics.user-events}", groupId = "graph-group")
    public void consumeUserEvents(UserEvent event) {
        log.info("Received user event of type: {} for user ID: {}", event.getEventType(), event.getUserId());

        if ("USER_CREATED".equals(event.getEventType())) {
            graphService.createUserNode(event.getUserId(), event.getUsername());
        } else if ("USER_DELETED".equals(event.getEventType())) {
            graphService.deleteUserNode(event.getUserId(), event.getUsername());
            log.info("User {} has been deleted", event.getUserId());
        }

    }

}