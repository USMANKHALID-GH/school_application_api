package com.zalisoft.zalisoft.interceptor;


import com.zalisoft.zalisoft.model.RestWsLog;
import com.zalisoft.zalisoft.service.RestWsLogService;
import com.zalisoft.zalisoft.util.JsonUtil;
import com.zalisoft.zalisoft.util.SpringApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        RestWsLogService restWsLogService = (RestWsLogService) SpringApplicationContext.getBeanByName("restWsLogService");
        RestWsLog restWsLog = new RestWsLog();
        ClientHttpResponse response;
        try {
            restWsLog.setUrl(request.getURI().toString());
//            restWsLog.setMethod(request.getMethodValue());
            restWsLog.setMethod(request.getMethod().name());
            restWsLog.setStartDate(LocalDateTime.now());
            restWsLog.setRequest(JsonUtil.formatJson(new String(body, StandardCharsets.UTF_8)));
            response = execution.execute(request, body);
            restWsLog.setSuccess(!response.getStatusCode().isError());
            restWsLog.setResponse(JsonUtil.formatJson(IOUtils.toString(response.getBody())));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            restWsLog.setSuccess(Boolean.FALSE);
            restWsLog.setErrorMsg(ExceptionUtils.getStackTrace(e));
            throw e;
        } finally {
            restWsLog.setEndDate(LocalDateTime.now());
            restWsLogService.save(restWsLog);
        }
        return response;
    }
}
