package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.DocumentDto;
import com.zalisoft.zalisoft.model.Documents;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDto, Documents> {
}
