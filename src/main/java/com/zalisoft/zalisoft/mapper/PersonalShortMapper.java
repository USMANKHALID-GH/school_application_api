package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.PersonalShortDto;
import com.zalisoft.zalisoft.model.Personal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonalShortMapper extends EntityMapper<PersonalShortDto , Personal> {

}
