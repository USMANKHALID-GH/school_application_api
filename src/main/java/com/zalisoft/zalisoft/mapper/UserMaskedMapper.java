package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.UserMaskedDto;
import com.zalisoft.zalisoft.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMaskedMapper extends EntityMapper<UserMaskedDto, User> {
}
