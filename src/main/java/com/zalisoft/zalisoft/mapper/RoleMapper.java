package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.RoleDto;
import com.zalisoft.zalisoft.model.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDto, Role> {

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);

    Set<RoleDto> toDto(Set<Role> roleList);

    Set<Role> toEntity(Set<RoleDto> roleDtoList);
}
