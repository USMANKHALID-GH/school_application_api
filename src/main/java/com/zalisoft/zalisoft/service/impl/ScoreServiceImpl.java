package com.zalisoft.zalisoft.service.impl;

import com.zalisoft.zalisoft.dto.ScoreDto;
import com.zalisoft.zalisoft.exception.BusinessException;
import com.zalisoft.zalisoft.model.Score;
import com.zalisoft.zalisoft.service.ParameterService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ScoreServiceImpl {

    @Autowired
    private ParameterService parameterService;



    public Score  getScore(ScoreDto dto){
        var gpaParameter=parameterService.getGpa();
        var alexParameter=parameterService.getAlex();
        var languageParameter=parameterService.getLANGUAGE();

        var score = new Score();
        if (ObjectUtils.isEmpty(dto.getAlexScore())
                ||
                (dto.getAlexScore().compareTo(getDecimalValue(alexParameter.getValue2())) < 0))  {
            throw new BusinessException("Alex Cannot be empty or less than: "+alexParameter.getValue2());
        }

        if (ObjectUtils.isEmpty(dto.getEnglishScore())
                ||
                (dto.getAlexScore().compareTo(getDecimalValue(languageParameter.getValue2())) < 0)) {
            throw new BusinessException("Language Cannot be empty or less than: "+languageParameter.getValue2());
        }

        if (ObjectUtils.isEmpty(dto.getGpaScore())
                ||
                (dto.getAlexScore().compareTo(getDecimalValue(gpaParameter.getValue2())) < 0)) {
            throw new BusinessException("GPA Cannot be empty or less than: "+gpaParameter.getValue2());
        }

        score.setAlexScore(dto.getAlexScore());
        score.setGpaScore(dto.getGpaScore());
        score.setEnglishScore(dto.getEnglishScore());
        BigDecimal total=calculateTotal(
                getDecimalValue(alexParameter.getValue2()),dto.getAlexScore(),
                getDecimalValue(gpaParameter.getValue2()),dto.getGpaScore(),
                getDecimalValue(languageParameter.getValue2()),dto.getEnglishScore());
        score.setTotalScore(total);
        return score;


    }


    private BigDecimal getDecimalValue(String value) {
        return new BigDecimal(value);
    }



    public static BigDecimal calculateTotal(BigDecimal alex, BigDecimal alexScore,
                                            BigDecimal gpa, BigDecimal gpaScore,
                                            BigDecimal language, BigDecimal languageScore) {
        return alex.multiply(alexScore)
                .add(gpa.multiply(gpaScore))
                .add(language.multiply(languageScore));
    }
}
