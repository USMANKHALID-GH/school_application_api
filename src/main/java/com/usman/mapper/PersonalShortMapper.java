package com.usman.mapper;

import com.usman.dto.PersonalShortDto;
import com.usman.model.Personal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonalShortMapper extends EntityMapper<PersonalShortDto, Personal> {

}
