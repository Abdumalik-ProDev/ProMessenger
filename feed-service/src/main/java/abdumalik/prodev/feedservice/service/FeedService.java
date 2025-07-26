package abdumalik.prodev.feedservice.service;

import abdumalik.prodev.feedservice.model.FeedItem;
import abdumalik.prodev.feedservice.model.UserFeed;
import abdumalik.prodev.feedservice.repository.UserFeedRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final UserFeedRepo userFeedRepository;

    public List<FeedItem> getUserFeed(UUID userId, int page, int size) {
        UserFeed userFeed = userFeedRepository.findById(userId).orElse(null);
        if (userFeed == null) {
            return Collections.emptyList();
        }
        // Basic pagination logic. In a real app, this would be more complex.
        List<FeedItem> allItems = userFeed.getFeedItems();
        int start = page * size;
        if (start >= allItems.size()) {
            return Collections.emptyList();
        }
        int end = Math.min(start + size, allItems.size());
        return allItems.subList(start, end);
    }

}