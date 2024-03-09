package com.usman.mapper;

import com.usman.dto.PrivilegeDto;
import com.usman.model.Privilege;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PrivilegeMapper extends EntityMapper<PrivilegeDto, Privilege> {
    PrivilegeDto toDto(Privilege privilege);

    Privilege toEntity(PrivilegeDto privilegeDto);

    Set<Privilege> toEntity(Set<PrivilegeDto> privilegeDtoList);

    Set<PrivilegeDto> toDto(Set<Privilege> privilegeList);
}
