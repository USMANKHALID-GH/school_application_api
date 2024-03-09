package com.usman.service.impl;


import com.usman.exception.BusinessException;
import com.usman.service.TemplateService;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

@Slf4j
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private Configuration freemarkerConfiguration;

    @Override
    public String createTemplate(Map<String, String> dataModel, String templateName) {
        log.info(templateName + " Template oluşturuluyor.");
        String templateContent;
        try {
            templateContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(templateName), dataModel);
        } catch (Exception e) {
            log.error("Template oluşturulurken hata alındı.", e);
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Template oluşturulurken hata alındı.");
        }
        return templateContent;
    }

}
