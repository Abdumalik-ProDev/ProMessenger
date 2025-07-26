package abdumalik.prodev.notificationservice.model;

import abdumalik.prodev.notificationservice.model.entity.NotificationType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "notifications")
@Data
public class Notification {

    @Id
    private String id;
    private UUID recipientId;
    private UUID senderId;
    private String senderUsername;
    private NotificationType type;
    private String message;
    private UUID entityId; // e.g., postId, commentId
    private boolean isRead = false;
    private LocalDateTime createdAt = LocalDateTime.now();

}