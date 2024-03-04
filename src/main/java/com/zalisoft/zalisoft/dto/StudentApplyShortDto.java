package com.zalisoft.zalisoft.dto;

import lombok.Data;

@Data
public class StudentApplyShortDto extends BaseDto{

    private Long id;
    private UserInformationDto userInformation;
    private UserMaskedDto user;
    private ScoreDto score;
    private DocumentDto document;
}
