package com.usman.mapper;

import com.usman.dto.StudentAppyDto;
import com.usman.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentApplyMapper extends EntityMapper<StudentAppyDto, Student> {
}
