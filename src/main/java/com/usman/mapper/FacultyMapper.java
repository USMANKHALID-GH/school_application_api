package com.usman.mapper;

import com.usman.dto.FacultyDto;
import com.usman.model.Faculty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyMapper extends EntityMapper<FacultyDto, Faculty> {
}
