package com.usman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseDto {
    private Long id;
    private String email;
    private String password;
    private Boolean active;
    private Date createdDate;
    private Set<RoleDto> roles;
}
