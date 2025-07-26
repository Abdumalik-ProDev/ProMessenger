package abdumalik.prodev.userservice.dto;

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

    private String eventType;
    private UUID userId;
    private String username;
    private String email;
    private String fullName;

}