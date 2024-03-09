package com.usman.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.usman.enums.ResponseMessageEnum;
import com.usman.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    public static String formatJson(String uglyJson) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement jsonElement = JsonParser.parseString(uglyJson);
            return gson.toJson(jsonElement);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return uglyJson;
    }


    public  static  <T> T  stringToJsonObject(String json, Class<T> class_ ){

        try {
            ObjectMapper objectMapper=new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, class_);
        }catch (Exception e){
            log.info("infor {}", e.getMessage());
            throw new BusinessException(ResponseMessageEnum.BACK_JSON_CONVERTOR_MSG_001);
        }

    }
}