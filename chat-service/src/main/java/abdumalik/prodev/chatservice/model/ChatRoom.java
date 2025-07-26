package abdumalik.prodev.chatservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "chat_rooms")
@Data
public class ChatRoom {

    @Id
    private String id; // The composite key: "userId1_userId2"
    private Set<Long> participantIds;
    private LocalDateTime lastMessageAt;

}