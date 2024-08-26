package com.demo.user.management.mapper;

import com.demo.user.management.dto.UserDto;
import com.demo.user.management.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto userToUserDTO(User user);

    List<UserDto> userToUserDTO(List<User> user);
}
