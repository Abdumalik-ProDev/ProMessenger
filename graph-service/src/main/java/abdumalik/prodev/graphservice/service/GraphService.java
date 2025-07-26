package abdumalik.prodev.graphservice.service;

import abdumalik.prodev.graphservice.dto.UserGraphDto;
import abdumalik.prodev.graphservice.mapper.UserMapper;
import abdumalik.prodev.graphservice.model.UserNode;
import abdumalik.prodev.graphservice.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraphService {

    private final UserRepo userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void createUserNode(UUID userId, String username) {
        if (userRepository.findByUserId(userId).isPresent()) {
            log.warn("User node with ID {} already exists. Skipping creation.", userId);
            return;
        }
        UserNode userNode = new UserNode(userId, username);
        userRepository.save(userNode);
        log.info("Created new user node for userId: {}", userId);
    }

    @Transactional
    public void deleteUserNode(UUID userId, String username) {
        if (userRepository.findByUserId(userId).isPresent()) {
            log.warn("User node with ID {} already exists. Skipping deletion.", userId);
            return;
        }
        UserNode userNode = new UserNode(userId, username);
        userRepository.deleteById(userId);
        log.info("Deleted user node for userId: {}", userId);
    }

    @Transactional
    public void followUser(UUID userId, UUID followId) {
        // Ensure both users exist before creating a relationship
        userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Follower user not found"));
        userRepository.findByUserId(followId).orElseThrow(() -> new RuntimeException("Followed user not found"));
        userRepository.follow(userId, followId);
        log.info("User {} is now following user {}", userId, followId);
    }

    @Transactional
    public void unfollowUser(UUID userId, UUID followId) {
        userRepository.unfollow(userId, followId);
        log.info("User {} unfollowed user {}", userId, followId);
    }

    @Transactional(readOnly = true)
    public List<UserGraphDto> getFollowing(UUID userId) {
        return userRepository.findFollowing(userId).stream()
                .map(userMapper::toUserGraphDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserGraphDto> getFollowers(UUID userId) {
        return userRepository.findFollowers(userId).stream()
                .map(userMapper::toUserGraphDTO)
                .collect(Collectors.toList());
    }

}