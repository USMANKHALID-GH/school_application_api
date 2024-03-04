package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.DepartmentDto;
import com.zalisoft.zalisoft.model.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDto, Department> {
}
