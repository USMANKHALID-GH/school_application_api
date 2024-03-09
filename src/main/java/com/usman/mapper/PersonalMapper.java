package com.usman.mapper;

import com.usman.dto.PersonalDto;
import com.usman.model.Personal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonalMapper extends EntityMapper<PersonalDto, Personal> {
}
