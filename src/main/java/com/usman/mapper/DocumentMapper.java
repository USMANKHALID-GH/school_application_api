package com.usman.mapper;

import com.usman.dto.DocumentDto;
import com.usman.model.Documents;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDto, Documents> {
}
