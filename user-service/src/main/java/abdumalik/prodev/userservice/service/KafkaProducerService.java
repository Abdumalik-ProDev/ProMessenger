package abdumalik.prodev.userservice.service;

import abdumalik.prodev.userservice.dto.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.user-events}")
    private String userEventsTopic;

    public void sendUserCreatedEvent(UserEvent event) {
        log.info("Publishing user created event for user ID: {}", event.getUserId());
        kafkaTemplate.send(userEventsTopic, event.getUserId().toString(), event);
    }

}