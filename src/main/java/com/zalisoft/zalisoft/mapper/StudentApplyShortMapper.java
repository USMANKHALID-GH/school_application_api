package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.StudentApplyShortDto;
import com.zalisoft.zalisoft.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentApplyShortMapper extends EntityMapper<StudentApplyShortDto, Student> {
}
