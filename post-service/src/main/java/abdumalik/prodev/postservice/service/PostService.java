package abdumalik.prodev.postservice.service;

import abdumalik.prodev.postservice.dto.PostCreateRequest;
import abdumalik.prodev.postservice.dto.PostEvent;
import abdumalik.prodev.postservice.dto.PostResponse;
import abdumalik.prodev.postservice.exception.PostNotFoundException;
import abdumalik.prodev.postservice.mapper.PostMapper;
import abdumalik.prodev.postservice.model.Hashtag;
import abdumalik.prodev.postservice.model.Post;
import abdumalik.prodev.postservice.repository.HashtagRepo;
import abdumalik.prodev.postservice.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private PostRepo postRepository;
    private HashtagRepo hashtagRepository;
    private final PostMapper postMapper;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public PostResponse createPost(PostCreateRequest request, UUID userId, String username) {
        log.info("Attempting to create post for user ID: {}", userId);
        Post post = new Post();
        post.setUserId(userId);
        post.setUsername(username);
        post.setCaption(request.getCaption());
        post.setMediaUrl(request.getMediaUrl());
        post.setType(request.getType());

        Set<Hashtag> hashtags = extractAndProcessHashtags(request.getCaption());
        post.setHashtags(hashtags);

        Post savedPost = postRepository.save(post);
        log.info("Successfully saved new post with ID: {} for user ID: {}", savedPost.getId(), userId);

        // Publish event to Kafka
        PostEvent event = new PostEvent(
                "POST_CREATED",
                savedPost.getId(),
                savedPost.getUserId(),
                savedPost.getUsername(),
                savedPost.getType(),
                savedPost.getMediaUrl(),
                savedPost.getCaption(),
                postMapper.hashtagsToString(savedPost.getHashtags()),
                LocalDateTime.now()
        );
        kafkaProducerService.sendPostEvent(event);

        return postMapper.toPostResponse(savedPost);
    }

    public PostResponse getPostById(UUID id) {
        log.info("Fetching post by ID: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Post not found for ID: {}", id);
                    return new PostNotFoundException("Post not found with id: " + id);
                });
        return postMapper.toPostResponse(post);
    }

    public List<PostResponse> getPostsByUserId(UUID userId) {
        log.info("Fetching all posts for user ID: {}", userId);
        return postRepository.findByUserId(userId).stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());
    }

    private Set<Hashtag> extractAndProcessHashtags(String caption) {
        if (caption == null || caption.isEmpty()) {
            return Set.of();
        }
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(caption);
        Set<String> hashtagNames = new HashSet<>();
        while (matcher.find()) {
            hashtagNames.add(matcher.group(1).toLowerCase());
        }

        return hashtagNames.stream()
                .map(name -> hashtagRepository.findByName(name)
                        .orElseGet(() -> {
                            log.debug("Creating new hashtag: {}", name);
                            return hashtagRepository.save(new Hashtag(name));
                        }))
                .collect(Collectors.toSet());
    }

}