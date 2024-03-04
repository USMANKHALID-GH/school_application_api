package com.zalisoft.zalisoft.dto;

import com.zalisoft.zalisoft.model.Department;
import com.zalisoft.zalisoft.model.User;
import com.zalisoft.zalisoft.model.UserInformation;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class PersonalDto  extends BaseDto{


    private Long id;
    private DepartmentDto department;
    private UserInformationDto userInformation;
    private UserDto user;
}
