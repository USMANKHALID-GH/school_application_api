package com.usman.mapper;

import com.usman.dto.StudentApplyShortDto;
import com.usman.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentApplyShortMapper extends EntityMapper<StudentApplyShortDto, Student> {
}
