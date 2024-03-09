package com.usman.dto;

import lombok.Data;

@Data
public class PersonalShortDto extends BaseDto{

    private Long id;
    private DepartmentDto department;
    private UserInformationDto userInformation;
    private UserMaskedDto user;
}
