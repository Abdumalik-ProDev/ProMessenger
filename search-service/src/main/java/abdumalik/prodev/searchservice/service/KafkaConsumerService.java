package abdumalik.prodev.searchservice.service;

import abdumalik.prodev.searchservice.dto.PostEvent;
import abdumalik.prodev.searchservice.dto.UserEvent;
import abdumalik.prodev.searchservice.model.PostDocument;
import abdumalik.prodev.searchservice.model.UserDocument;
import abdumalik.prodev.searchservice.repository.PostSearchRepo;
import abdumalik.prodev.searchservice.repository.UserSearchRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final UserSearchRepo userSearchRepository;
    private final PostSearchRepo postSearchRepository;

    @KafkaListener(topics = "${app.kafka.topics.user-events}", groupId = "search-group", containerFactory = "userKafkaListenerContainerFactory")
    public void consumeUserEvents(UserEvent event) {
        log.info("Consumed user event of type: {} for user ID: {}", event.getEventType(), event.getUserId());
        if ("USER_CREATED".equals(event.getEventType()) || "USER_UPDATED".equals(event.getEventType())) {
            UserDocument userDocument = new UserDocument();
            userDocument.setUserId(event.getUserId());
            userDocument.setUsername(event.getUsername());
            userDocument.setFullName(event.getFullName());
            userSearchRepository.save(userDocument);
            log.info("Indexed user document for user ID: {}", event.getUserId());
        } else if ("USER_DELETED".equals(event.getEventType())) {
            userSearchRepository.deleteById(event.getUserId());
        }
    }

    @KafkaListener(topics = "${app.kafka.topics.post-events}", groupId = "search-group", containerFactory = "postKafkaListenerContainerFactory")
    public void consumePostEvents(PostEvent event) {
        log.info("Consumed post event of type: {} for post ID: {}", event.getEventType(), event.getPostId());
        if ("POST_CREATED".equals(event.getEventType()) || "POST_UPDATED".equals(event.getEventType())) {
            PostDocument postDocument = new PostDocument();
            postDocument.setPostId(event.getPostId());
            postDocument.setCaption(event.getCaption());
            postDocument.setHashtags(event.getHashtags());
            postDocument.setUserId(event.getUserId());
            postSearchRepository.save(postDocument);
            log.info("Indexed post document for post ID: {}", event.getPostId());
        } else if ("POST_DELETED".equals(event.getEventType())) {
            postSearchRepository.deleteById(event.getPostId());
        }

    }

}