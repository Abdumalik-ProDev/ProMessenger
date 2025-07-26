package abdumalik.prodev.feedservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private UUID id;
    private String username;
    private List<UUID> followers;  // The list of user IDs that follow this user

}