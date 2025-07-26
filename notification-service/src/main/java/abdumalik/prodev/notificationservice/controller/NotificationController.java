package abdumalik.prodev.notificationservice.controller;

import abdumalik.prodev.notificationservice.model.Notification;
import abdumalik.prodev.notificationservice.repository.NotificationRepo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "API for retrieving notification history")
public class NotificationController {

    private final NotificationRepo notificationRepository;

    @GetMapping("/me")
    public ResponseEntity<Page<Notification>> getMyNotifications(
            @RequestHeader("X-Authenticated-Username") String username,
            // Again, this would be mapped from the username.
            // For now, passing userId directly.
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationPage = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId, pageable);
        return ResponseEntity.ok(notificationPage);
    }

}