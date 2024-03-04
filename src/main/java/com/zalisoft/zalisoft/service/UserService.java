package com.zalisoft.zalisoft.service;

import com.zalisoft.zalisoft.dto.UserDto;
import com.zalisoft.zalisoft.dto.UserInformationDto;
import com.zalisoft.zalisoft.model.Role;
import com.zalisoft.zalisoft.model.User;
import com.zalisoft.zalisoft.model.UserInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    User registerPersonal( UserDto userDto);

    User registerStudent( UserDto userDto);

    User findOneByEmail(String loginOrEmail);

    User findById(Long id);


    Page<User> search(String searchText, String phoneNumber, Pageable pageable);

    void changePassword(String currentClearTextPassword, String newPassword);

    void deactivateUser(Long userId);

    User getCurrentUser();

    User updateCurrentUser(UserDto userDto);

    User updateUserByAdmin(Long userId, UserDto userDto);

    void activateUser(Long userId);


    UserInformation  userValidation(UserInformationDto dto);

    UserInformation updateUserInformation(UserInformation infor,UserInformationDto dto);

    void deleteByEmail(String email);

    void  assignRole(Long id,Role role);
}
