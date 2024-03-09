package com.usman.dto;

import lombok.Data;

@Data
public class DocumentDto extends BaseDto{
    private Long id;
    private String certificateUrl;
    private String transcriptUrl;
    private String userImageUrl;
    private String  englishUrl;
}
