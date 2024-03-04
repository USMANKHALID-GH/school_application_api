package com.zalisoft.zalisoft.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMaskedDto extends BaseDto {
    private Long id;
    private String email;
    private Set<RoleDto> roles;

}
