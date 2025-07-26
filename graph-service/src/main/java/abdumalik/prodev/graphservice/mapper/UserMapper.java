package abdumalik.prodev.graphservice.mapper;

import abdumalik.prodev.graphservice.dto.UserGraphDto;
import abdumalik.prodev.graphservice.model.UserNode;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserGraphDto toUserGraphDTO(UserNode userNode) {
        if (userNode == null) {
            return null;
        }
        return new UserGraphDto(userNode.getUserId(), userNode.getUsername());
    }

}