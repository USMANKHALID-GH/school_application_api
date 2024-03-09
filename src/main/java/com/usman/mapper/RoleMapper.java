package com.usman.mapper;

import com.usman.dto.RoleDto;
import com.usman.model.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDto, Role> {

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);

    Set<RoleDto> toDto(Set<Role> roleList);

    Set<Role> toEntity(Set<RoleDto> roleDtoList);
}
