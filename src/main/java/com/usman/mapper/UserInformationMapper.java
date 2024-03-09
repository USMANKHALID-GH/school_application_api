package com.usman.mapper;

import com.usman.dto.UserInformationDto;
import com.usman.model.UserInformation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInformationMapper  extends BaseMapper<UserInformationDto, UserInformation> {
}
