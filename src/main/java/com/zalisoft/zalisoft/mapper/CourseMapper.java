package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.CoursesDto;
import com.zalisoft.zalisoft.model.Courses;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<CoursesDto, Courses> {
}
