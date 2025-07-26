package abdumalik.prodev.userservice.mapper;

import abdumalik.prodev.userservice.dto.UserDto;
import abdumalik.prodev.userservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

}