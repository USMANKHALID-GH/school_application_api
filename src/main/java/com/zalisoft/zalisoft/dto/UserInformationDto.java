package com.zalisoft.zalisoft.dto;


import lombok.Data;

@Data
public class UserInformationDto extends BaseDto
{

    private String firstName;
    private String lastName;
    private String phone;
    private String tcNumber;
    private String birthDate;
    private String qualification;
    private AddressDto address;
}
