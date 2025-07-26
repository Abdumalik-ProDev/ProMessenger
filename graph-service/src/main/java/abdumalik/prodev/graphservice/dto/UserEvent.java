package abdumalik.prodev.graphservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEvent {

    private String eventType; // e.g., "USER_CREATED"
    private UUID userId;
    private String username;
    private String email;

}