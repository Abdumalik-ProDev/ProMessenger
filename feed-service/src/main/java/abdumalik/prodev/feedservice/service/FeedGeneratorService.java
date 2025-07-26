package abdumalik.prodev.feedservice.service;

import abdumalik.prodev.feedservice.client.UserServiceClient;
import abdumalik.prodev.feedservice.dto.PostEvent;
import abdumalik.prodev.feedservice.model.FeedItem;
import abdumalik.prodev.feedservice.model.UserFeed;
import abdumalik.prodev.feedservice.repository.UserFeedRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedGeneratorService {

    private final UserFeedRepo userFeedRepository;
    private final UserServiceClient userServiceClient;
    private static final int FEED_LIMIT = 100; // Keep only the latest 100 items in the feed

    public void handlePostCreated(PostEvent postEvent) {
        UUID authorId = postEvent.getUserId();
        log.info("Processing new post {} from user {}", postEvent.getPostId(), authorId);

        // 1. Fetch the list of followers for the author of the post
        List<UUID> followerIds;
        try {
            // In a real system, add resilience (Resilience4j Circuit Breaker/Retry)
            followerIds = userServiceClient.getFollowers(authorId);
            log.info("Found {} followers for user {}", followerIds.size(), authorId);
        } catch (Exception e) {
            log.error("Failed to fetch followers for user {}. Aborting feed fan-out.", authorId, e);
            // In production, you might requeue this event to a dead-letter queue for later processing
            return;
        }


        // 2. Create the new feed item
        FeedItem newItem = new FeedItem(postEvent.getPostId(), postEvent.getUserId() ,postEvent.getTimestamp());

        // 3. Fan-out: Add the new post to each follower's feed
        for (UUID followerId : followerIds) {
            updateFollowerFeed(followerId, newItem);
        }

        // 4. (Optional) Add the post to the author's own feed as well
        updateFollowerFeed(authorId, newItem);
    }

    public void updateFollowerFeed(UUID userId, FeedItem newItem) {
        UserFeed userFeed = userFeedRepository.findById(userId)
                .orElseGet(() -> {
                    UserFeed newFeed = new UserFeed();
                    newFeed.setId(userId);
                    return newFeed;
                });

        // Add the new item to the beginning of the list
        userFeed.getFeedItems().add(0, newItem);

        // Trim the feed to maintain a fixed size
        if (userFeed.getFeedItems().size() > FEED_LIMIT) {
            userFeed.getFeedItems().remove(userFeed.getFeedItems().size() - 1);
        }

        userFeedRepository.save(userFeed);
        log.debug("Updated feed for user {}", userId);
    }

    public void deleteFollowerFeed(UUID userId, FeedItem newItem) {
        Optional<UserFeed> userFeed = userFeedRepository.findById(userId);
        if (userFeed.isPresent()) {
            boolean remove = userFeed.get().getFeedItems().remove(newItem);
            if (remove) {
                userFeedRepository.save(userFeed.get());
                log.debug("Deleted feed for user {}", userId);
            } else {
                log.error("Failed to delete feed for user {}", userId);
            }

        }
        log.debug("Deleted feed for user {}", userId);
    }

}