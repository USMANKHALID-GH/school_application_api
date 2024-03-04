package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.UserInformationDto;
import com.zalisoft.zalisoft.model.UserInformation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInformationMapper  extends BaseMapper<UserInformationDto, UserInformation> {
}
