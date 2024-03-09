package com.usman.dto;

import com.usman.enums.ApplicationStatus;
import lombok.Data;

@Data
public class StudentApplyShortDto extends BaseDto{

    private Long id;
    private UserInformationDto userInformation;
    private UserMaskedDto user;
    private ScoreDto score;
    private DocumentDto document;
    private ApplicationStatus applicationStatus;
}
