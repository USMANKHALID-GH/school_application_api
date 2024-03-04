package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.FacultyDto;
import com.zalisoft.zalisoft.model.Faculty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyMapper extends EntityMapper<FacultyDto, Faculty> {
}
