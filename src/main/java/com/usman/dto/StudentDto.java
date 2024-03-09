package com.usman.dto;

import lombok.Data;

@Data
public class StudentDto extends BaseDto
{

    private Long id;
    private UserInformationDto userInformation;
    private UserMaskedDto user;

}
