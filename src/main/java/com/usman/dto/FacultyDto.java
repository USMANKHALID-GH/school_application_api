package com.usman.dto;

import lombok.Data;

@Data
public class FacultyDto extends BaseDto {
    private Long id;
    private String name;
    private String description;
    private String code;
//    private List<DepartmentDto> department;
}
