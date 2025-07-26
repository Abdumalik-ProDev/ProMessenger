package abdumalik.prodev.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private UUID id;
    private String username;
    private String fullName;
    private String bio;
    private String profilePictureUrl;
    private LocalDateTime createdAt;

}