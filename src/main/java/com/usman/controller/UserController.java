package com.usman.controller;


import com.usman.dto.BaseResponseDto;
import com.usman.dto.PasswordChangeDto;
import com.usman.dto.UserDto;

import com.usman.mapper.RoleMapper;
import com.usman.mapper.UserMapper;
import com.usman.model.User;
import com.usman.service.UserService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;



    @Autowired
    private RoleMapper roleMapper;

    @GetMapping("/super/user/search")
    public Page<UserDto> search(@RequestParam(value = "searchText", required = false) String searchText, @RequestParam(value = "phoneNumber", required = false) String phoneNumber, Pageable pageable) {
        Page<User> searchResult = userService.search(searchText, phoneNumber, pageable);
        return new PageImpl<>(userMapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements());
    }


    @PutMapping("/super/user/{userId}/deactivate")
    public ResponseEntity<BaseResponseDto> deactivateUser(@PathVariable Long userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Kullanıcı başarılı bir şekilde pasif hale getirilmiştir.").build());
    }


    @PutMapping("/super/user/{userId}/activate")
    public ResponseEntity<BaseResponseDto> activateUser(@PathVariable Long userId) {
        userService.activateUser(userId);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Kullanıcı başarılı bir şekilde aktif hale getirilmiştir.").build());
    }


    @GetMapping("/admin/user/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable Long userId) {
        UserDto userDto = userMapper.toDto(userService.findById(userId));
        return ResponseEntity.ok(userDto);
    }


    @PutMapping("/admin/user/{userId}")
    public ResponseEntity<UserDto> updateUserByAdmin(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userMapper.toDto(userService.updateUserByAdmin(userId, userDto)));
    }


    @GetMapping("/user/current")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(userMapper.toDto(userService.getCurrentUser()));
    }


    @PutMapping("/user/current")
    public ResponseEntity<UserDto> updateCurrentUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userMapper.toDto(userService.updateCurrentUser(userDto)));
    }


    @PutMapping("/user/current/change-password")
    public ResponseEntity<BaseResponseDto> changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        userService.changePassword(passwordChangeDto.getOldPassword(), passwordChangeDto.getNewPassword());
        return ResponseEntity.ok(BaseResponseDto.builder().message("Şifre güncellenmiştir.").build());
    }


}
