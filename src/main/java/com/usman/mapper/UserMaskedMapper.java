package com.usman.mapper;

import com.usman.dto.UserMaskedDto;
import com.usman.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMaskedMapper extends EntityMapper<UserMaskedDto, User> {
}
