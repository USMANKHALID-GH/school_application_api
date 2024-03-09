package com.usman.dto;

import lombok.Data;

@Data
public class PersonalDto  extends BaseDto{


    private Long id;
    private DepartmentDto department;
    private UserInformationDto userInformation;
    private UserDto user;
}
