package com.zalisoft.zalisoft.dto;

import com.zalisoft.zalisoft.model.User;
import com.zalisoft.zalisoft.model.UserInformation;
import lombok.Data;

@Data
public class StudentDto extends BaseDto
{

    private Long id;
    private UserInformationDto userInformation;
    private UserMaskedDto user;

}
