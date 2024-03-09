package com.usman.handler;


import com.usman.dto.BaseResponseDto;
import com.usman.enums.ResponseMessageEnum;
import com.usman.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

import java.util.List;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public final ResponseEntity<BaseResponseDto> handleException(BusinessException e) {
        return createResponse(e.getHttpStatus(), e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "", e);
    }

    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<BaseResponseDto> handleException(Exception e) {
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessageEnum.BACK_SYSTEM_ERROR_MSG_001.message(), ResponseMessageEnum.BACK_SYSTEM_ERROR_MSG_001.messageDetail(), e);
    }



    //newly added method
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<BaseResponseDto>> hibernateModelValidation(MethodArgumentNotValidException error){
        List<BaseResponseDto> baseResponseDtos=new ArrayList<>();
        error.getBindingResult().getFieldErrors()
                .forEach(fieldError ->baseResponseDtos.add(createResponse(HttpStatus.BAD_REQUEST,fieldError.getField(),fieldError.getDefaultMessage(),error).getBody()));
        return ResponseEntity.ok(baseResponseDtos);
    }




    private  ResponseEntity<BaseResponseDto> createResponse(HttpStatus httpStatus, String message, String detailMessage, Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(httpStatus).body(BaseResponseDto.builder().
                statusCode(httpStatus.value()).status(httpStatus.name()).message(message).messageDetail(detailMessage).success(false).build());
    }


}