package abdumalik.prodev.searchservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEvent {

    private String eventType;
    private UUID userId;
    private String username;
    private String fullName;

}