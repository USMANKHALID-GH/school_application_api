package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.StudentDto;
import com.zalisoft.zalisoft.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper extends EntityMapper<StudentDto,Student> {
}
