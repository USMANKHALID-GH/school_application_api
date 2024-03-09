package com.usman.mapper;

import com.usman.dto.StudentDto;
import com.usman.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper extends EntityMapper<StudentDto, Student> {
}
