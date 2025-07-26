package abdumalik.prodev.feedservice.controller;

import abdumalik.prodev.feedservice.model.FeedItem;
import abdumalik.prodev.feedservice.service.FeedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
@Tag(name = "Feeds", description = "API for retrieving personalized user feeds")
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/me")
    public ResponseEntity<List<FeedItem>> getMyFeed(
            // The username is passed by the API Gateway after validating the JWT
            @RequestHeader("X-Authenticated-Username") String username,
            // We need a way to map username to userId. This would be another client call.
            // For now, we'll pass the userId directly for simplicity.
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<FeedItem> feed = feedService.getUserFeed(userId, page, size);
        return ResponseEntity.ok(feed);
    }

}