package abdumalik.prodev.feedservice.service;

import abdumalik.prodev.feedservice.dto.PostEvent;
import abdumalik.prodev.feedservice.model.FeedItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final FeedGeneratorService feedGeneratorService;

    private FeedItem feedItem;

    @KafkaListener(topics = "${app.kafka.topics.post-events}", groupId = "feed-group")
    public void consumePostEvents(PostEvent event) {
        log.info("Received event of type: {} for post ID: {}", event.getEventType(), event.getPostId());

        if ("POST_CREATED".equals(event.getEventType())) {
            feedGeneratorService.handlePostCreated(event);
        } else if ("POST_UPDATED".equals(event.getEventType())) {
            feedGeneratorService.updateFollowerFeed(event.getPostId(), feedItem);
        } else if ("POST_DELETED".equals(event.getEventType())) {
            feedGeneratorService.deleteFollowerFeed(event.getPostId(), feedItem);
        }

    }

}