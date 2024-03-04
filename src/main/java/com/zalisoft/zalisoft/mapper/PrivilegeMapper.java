package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.PrivilegeDto;
import com.zalisoft.zalisoft.model.Privilege;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PrivilegeMapper extends EntityMapper<PrivilegeDto, Privilege> {
    PrivilegeDto toDto(Privilege privilege);

    Privilege toEntity(PrivilegeDto privilegeDto);

    Set<Privilege> toEntity(Set<PrivilegeDto> privilegeDtoList);

    Set<PrivilegeDto> toDto(Set<Privilege> privilegeList);
}
