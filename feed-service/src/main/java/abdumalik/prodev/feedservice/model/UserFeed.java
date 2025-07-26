package abdumalik.prodev.feedservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user_feeds")
public class UserFeed {

    @Id
    private UUID id;  // The user ID this feed belongs to

    // Using a LinkedList for efficient prepending (LIFO)
    private List<FeedItem> feedItems = new LinkedList<>();


}