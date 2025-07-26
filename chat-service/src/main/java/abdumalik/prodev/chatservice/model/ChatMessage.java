package abdumalik.prodev.chatservice.model;

import abdumalik.prodev.chatservice.model.entity.MessageStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "chat_messages")
@Data
public class ChatMessage {

    @Id
    private String id;
    private String chatRoomId; // To group messages by conversation
    private UUID senderId;
    private String senderUsername;
    private UUID recipientId;
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();
    private MessageStatus status;

}