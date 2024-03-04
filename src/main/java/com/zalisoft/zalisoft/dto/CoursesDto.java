package com.zalisoft.zalisoft.dto;

import lombok.Data;


@Data
public class CoursesDto extends BaseDto{
    private Long id;
    private String name;
    private String code;
    private DepartmentDto department;
}
