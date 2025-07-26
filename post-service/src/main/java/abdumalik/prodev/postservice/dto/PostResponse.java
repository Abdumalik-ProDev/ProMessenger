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
public class PostResponse {

    private UUID id;
    private UUID userId;
    private String username;
    private String caption;
    private String mediaUrl;
    private PostType type;
    private Set<String> hashtags;
    private LocalDateTime createdAt;

}