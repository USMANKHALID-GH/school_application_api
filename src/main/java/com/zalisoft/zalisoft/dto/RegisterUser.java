package com.zalisoft.zalisoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUser {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private Long departmentId;
    private String qualification;
}
