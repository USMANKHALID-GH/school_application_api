package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.ParameterDto;
import com.zalisoft.zalisoft.model.Parameter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParameterMapper extends EntityMapper<ParameterDto, Parameter> {

    ParameterDto toDto(Parameter parameter);

    Parameter toEntity(ParameterDto parameterDto);

}
