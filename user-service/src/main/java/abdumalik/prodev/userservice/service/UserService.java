package abdumalik.prodev.userservice.service;

import abdumalik.prodev.userservice.dto.RegisterRequest;
import abdumalik.prodev.userservice.dto.UserDto;
import abdumalik.prodev.userservice.dto.UserEvent;
import abdumalik.prodev.userservice.exception.UserAlreadyExistsException;
import abdumalik.prodev.userservice.mapper.UserMapper;
import abdumalik.prodev.userservice.model.User;
import abdumalik.prodev.userservice.model.entity.AuthProvider;
import abdumalik.prodev.userservice.repository.UserRepo;
import abdumalik.prodev.userservice.security.oauth2.OAuth2UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;
    private PasswordEncoder passwordEncoder;
    private final UserMapper mapper;
    private final KafkaProducerService kafkaProducerService;

    private void publishUserEvent(String eventType, User user) {
        UserEvent event = new UserEvent(
                eventType,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName()
        );
        kafkaProducerService.sendUserCreatedEvent(event);
    }

    private String generateUniqueUsername(String email) {
        String baseUsername = email.split("@")[0];
        String finalUsername = baseUsername;
        int counter = 1;
        while (repo.findByUsername(finalUsername).isPresent()) {
            finalUsername = baseUsername + counter;
            counter++;
        }
        return finalUsername;
    }

    private User registerNewOAuth2User(OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setProvider(AuthProvider.valueOf(oAuth2UserInfo.getProvider()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setFullName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setUsername(generateUniqueUsername(oAuth2UserInfo.getEmail()));
        user.setProfilePictureUrl(oAuth2UserInfo.getImageUrl());
        User savedUser = repo.save(user);
        publishUserEvent("USER_CREATED", savedUser);
        return savedUser;
    }

    private User updateUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFullName(oAuth2UserInfo.getName());
        existingUser.setProfilePictureUrl(oAuth2UserInfo.getImageUrl());
        existingUser.setUpdatedAt(LocalDateTime.now());
        User updatedUser = repo.save(existingUser);
        publishUserEvent("USER_UPDATED", updatedUser);
        return updatedUser;
    }

    @Transactional
    public UserDto registerUser(RegisterRequest registerRequest) {
        if (repo.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username " + registerRequest.getUsername() + " is already exists");
        }

        if (repo.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email " + registerRequest.getEmail() + " is already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setProvider(AuthProvider.local);

        User save = repo.save(user);

        publishUserEvent("USER_CREATED", save);

        return mapper.toUserDto(save);

    }

    @Transactional
    public User processOAuth2User(OAuth2UserInfo oAuth2UserInfo) {
        User user = repo.findByEmail(oAuth2UserInfo.getEmail())
                .map(existingUser -> {
                    if (!existingUser.getProvider().equals(AuthProvider.valueOf(oAuth2UserInfo.getProvider()))) {
                        throw new UserAlreadyExistsException("Looks like you're signed up with " + existingUser.getUsername() + " account. Please use your " + existingUser.getProvider() + " account to login.");
                    }
                    return updateUser(existingUser, oAuth2UserInfo);
                })
                .orElseGet(() -> registerNewOAuth2User(oAuth2UserInfo));
        return user;
    }


    public UserDto findUserByUsername(String username) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
        return mapper.toUserDto(user);
    }

}