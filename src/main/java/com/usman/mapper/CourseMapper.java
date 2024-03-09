package com.usman.mapper;

import com.usman.dto.CoursesDto;
import com.usman.model.Courses;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<CoursesDto, Courses> {
}
