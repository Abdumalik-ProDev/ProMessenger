package abdumalik.prodev.feedservice.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

// This DTO must match the structure of the event produced by the Post Service
@Data
public class PostEvent {
    private String eventType;
    private UUID postId;
    private UUID userId;
    // We don't need all fields, just the ones relevant to feed generation
    private LocalDateTime timestamp;
}