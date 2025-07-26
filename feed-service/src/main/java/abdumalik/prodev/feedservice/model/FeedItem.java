package abdumalik.prodev.feedservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedItem {

    private UUID postId;
    private UUID userId;
    private LocalDateTime postCreatedAt;

}