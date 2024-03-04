package com.zalisoft.zalisoft.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScoreDto extends BaseDto{
    private Long id;
    private BigDecimal alexScore;
    private BigDecimal gpaScore;
    private BigDecimal englishScore;
    private BigDecimal totalScore;

}
