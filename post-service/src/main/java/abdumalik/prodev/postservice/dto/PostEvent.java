package abdumalik.prodev.postservice.dto;

import abdumalik.prodev.postservice.model.entity.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostEvent {


    private String eventType;
    private UUID postId;
    private UUID userId;
    private String username;
    private PostType type;
    private String mediaUrl;
    private String caption;
    private Set<String> hashtags;
    private LocalDateTime timestamp;

}