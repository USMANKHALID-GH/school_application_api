package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.PersonalDto;
import com.zalisoft.zalisoft.model.Personal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonalMapper extends EntityMapper<PersonalDto, Personal> {
}
