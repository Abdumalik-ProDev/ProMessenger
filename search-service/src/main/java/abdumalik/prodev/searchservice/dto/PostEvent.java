package abdumalik.prodev.searchservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.Set;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostEvent {

    private String eventType;
    private UUID postId;
    private String caption;
    private UUID userId;
    private Set<String> hashtags;

}