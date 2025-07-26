package abdumalik.prodev.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

// A generic DTO to capture the event type before deserializing to a specific event object
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore fields we don't need
public class GenericEvent {

    private String eventType;
    private UUID userId; // User who initiated the event (e.g., liker, follower)
    private String username;
    private UUID targetUserId; // User who is the target of the event (e.g., post owner)
    private UUID entityId; // ID of the post, comment, etc.

}