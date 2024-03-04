package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.StudentAppyDto;
import com.zalisoft.zalisoft.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentApplyMapper extends EntityMapper<StudentAppyDto, Student> {
}
