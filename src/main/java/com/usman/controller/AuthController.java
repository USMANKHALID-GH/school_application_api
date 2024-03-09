package com.usman.controller;


import com.uploadcare.upload.UploadFailureException;
import com.usman.dto.AuthToken;
import com.usman.dto.BaseResponseDto;
import com.usman.dto.PersonalDto;
import com.usman.dto.StudentAppyDto;
import com.usman.enums.ResponseMessageEnum;
import com.usman.exception.BusinessException;
import com.usman.security.jwt.AuthRequest;
import com.usman.security.jwt.TokenProvider;
import com.usman.service.PersonalService;
import com.usman.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.usman.util.JsonUtil.stringToJsonObject;


@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;


    @Autowired
    private PersonalService personalService;


    @Autowired
    private StudentService studnetService;

    @PostMapping("/auth/login")
    public AuthToken login(@Valid @RequestBody AuthRequest authRequest) {
        log.info("Login: {}", authRequest.getPassword());
        AuthToken authToken;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
            log.info("auth : {} ", authenticationToken);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("auth : {} ", authentication.getDetails());
            authToken = tokenProvider.createToken(authentication);
            log.info("auth token : {}", authToken.getJwtToken());
        } catch (AuthenticationException e) {
            throw new BusinessException(ResponseMessageEnum.BACK_USER_MSG_007);
        }

        log.info("Login başarılı. Email: {}", authRequest.getEmail());
        return authToken;
    }

    @PostMapping("/super/register/personal")
    public BaseResponseDto registerPersonal(@RequestBody PersonalDto personalDto, @RequestParam Long departmentId) {
       personalService.savePersonal(personalDto, departmentId);
        return BaseResponseDto.builder().message("Kullanıcı oluşturma işleminiz başarılı gerçekleşmiştir.")
                .statusCode(HttpStatus.CREATED.value()).status(HttpStatus.CREATED.name()).build();
    }


    @PostMapping("/public/register/student/department/{id}")
    public BaseResponseDto studentApply(@RequestParam String dto,
                                        @PathVariable("id") Long departmentId,
                                        @RequestParam  MultipartFile diploma,
                                        @RequestParam  MultipartFile english,
                                        @RequestParam  MultipartFile transcript,
                                        @RequestParam  MultipartFile image) throws UploadFailureException, IOException {
        Map<String ,MultipartFile> files= new HashMap<>();
        files.put("diploma",diploma);
        files.put("english",english);
        files.put("transcript",transcript);
        files.put("image",image);
        var document=List.of(files);
        var studentDo=stringToJsonObject(dto, StudentAppyDto.class);
        studnetService.apply(studentDo, document,departmentId);
        return BaseResponseDto.builder().message("Kullanıcı oluşturma işleminiz başarılı gerçekleşmiştir.")
                .statusCode(HttpStatus.CREATED.value()).status(HttpStatus.CREATED.name()).build();
    }

}