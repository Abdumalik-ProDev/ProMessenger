package abdumalik.prodev.postservice.controller;

import abdumalik.prodev.postservice.dto.PostCreateRequest;
import abdumalik.prodev.postservice.dto.PostResponse;
import abdumalik.prodev.postservice.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "APIs for creating and retrieving posts, stories, and reels")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostCreateRequest request,
            @RequestHeader("X-Authenticated-User-Id") UUID userId,
            @RequestHeader("X-Authenticated-Username") String username) {
        PostResponse createdPost = postService.createPost(request, userId, username);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getPostsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

}