package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.UserDto;
import com.zalisoft.zalisoft.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    Set<UserDto> toDto(Set<User> userList);

    Set<User> toEntity(Set<UserDto> userDtoList);
}
