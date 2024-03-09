package com.usman.dto;

import lombok.Data;

@Data
public class StudentAppyDto extends BaseDto{

    private Long id;
    private UserInformationDto userInformation;
    private UserDto user;
    private ScoreDto score;
    private DocumentDto document;
}
