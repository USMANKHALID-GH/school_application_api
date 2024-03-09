package com.usman.dto;


import lombok.Data;

@Data
public class DepartmentDto extends BaseDto{

    private Long id;
    private String name;
    private String code;

}
