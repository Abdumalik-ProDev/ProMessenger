package abdumalik.prodev.postservice.service;

import abdumalik.prodev.postservice.dto.PostEvent;
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

    @Value("${app.kafka.topics.post-events}")
    private String postEventsTopic;

    public void sendPostEvent(PostEvent event) {
        log.info("Publishing event {} for post ID: {}", event.getEventType(), event.getPostId());
        try {
            kafkaTemplate.send(postEventsTopic, event.getPostId().toString(), event);
        } catch (Exception e) {
            log.error("Failed to send event to Kafka for post ID: {}", event.getPostId(), e);
        }
    }

}