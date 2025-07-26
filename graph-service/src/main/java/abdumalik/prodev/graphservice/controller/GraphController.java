package abdumalik.prodev.graphservice.controller;

import abdumalik.prodev.graphservice.dto.UserGraphDto;
import abdumalik.prodev.graphservice.service.GraphService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/graph")
@RequiredArgsConstructor
@Tag(name = "Graph", description = "APIs for managing and querying the social graph")
public class GraphController {

    private final GraphService graphService;

    @PostMapping("/users/{userId}/follow/{followId}")
    public ResponseEntity<Void> follow(@PathVariable UUID userId, @PathVariable UUID followId) {
        graphService.followUser(userId, followId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/users/{userId}/follow/{followId}")
    public ResponseEntity<Void> unfollow(@PathVariable UUID userId, @PathVariable UUID followId) {
        graphService.unfollowUser(userId, followId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/following")
    public ResponseEntity<List<UserGraphDto>> getFollowing(@PathVariable UUID userId) {
        return ResponseEntity.ok(graphService.getFollowing(userId));
    }

    @GetMapping("/users/{userId}/followers")
    public ResponseEntity<List<UserGraphDto>> getFollowers(@PathVariable UUID userId) {
        // This is the endpoint the Feed Service's Feign client will now call.
        return ResponseEntity.ok(graphService.getFollowers(userId));
    }

}