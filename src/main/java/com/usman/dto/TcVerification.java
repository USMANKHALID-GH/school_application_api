package com.usman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TcVerification extends BaseDto {
    private String tcNumber;
    private String firstName;
    private String lastName;
    private Integer yearOfBirth;
}
