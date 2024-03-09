package com.usman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDto extends BaseDto {

    private Long id;
    private String key;
    private String value;
    private String value2;
    private String description;
}
