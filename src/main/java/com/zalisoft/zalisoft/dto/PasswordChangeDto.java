package com.zalisoft.zalisoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDto extends BaseDto {
    private String oldPassword;
    private String newPassword;
}
