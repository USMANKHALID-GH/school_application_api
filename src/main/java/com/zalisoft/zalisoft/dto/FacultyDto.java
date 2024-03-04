package com.zalisoft.zalisoft.dto;

import lombok.Data;

import java.util.List;

@Data
public class FacultyDto extends BaseDto {
    private Long id;
    private String name;
    private String description;
    private String code;
//    private List<DepartmentDto> department;
}
