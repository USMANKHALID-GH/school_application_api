package com.usman.mapper;

import com.usman.dto.ParameterDto;
import com.usman.model.Parameter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParameterMapper extends EntityMapper<ParameterDto, Parameter> {

    ParameterDto toDto(Parameter parameter);

    Parameter toEntity(ParameterDto parameterDto);

}
