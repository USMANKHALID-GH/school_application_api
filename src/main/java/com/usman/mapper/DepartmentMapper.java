package com.usman.mapper;

import com.usman.dto.DepartmentDto;
import com.usman.model.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDto, Department> {
}
