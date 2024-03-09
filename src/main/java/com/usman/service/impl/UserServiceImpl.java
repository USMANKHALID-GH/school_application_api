package com.usman.service.impl;

import com.usman.dto.RoleDto;
import com.usman.dto.UserDto;
import com.usman.dto.UserInformationDto;
import com.usman.enums.ResponseMessageEnum;
import com.usman.exception.BusinessException;
import com.usman.exception.InvalidPasswordException;
import com.usman.model.Address;
import com.usman.model.Role;
import com.usman.model.User;
import com.usman.model.UserInformation;
import com.usman.repository.UserRepository;
import com.usman.util.SecurityUtils;
import com.zalisoft.zalisoft.dto.*;
import com.usman.mapper.UserInformationMapper;
import com.zalisoft.zalisoft.model.*;
import com.usman.service.RoleService;
import com.usman.service.UserService;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



@Slf4j
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserInformationMapper informationMapper;



    @Override
    public User registerPersonal(UserDto userDto) {
        User newUser = register(userDto);
        newUser.setRoles(new HashSet<>(Set.of(roleService.findByName("ADMIN"))));
        try {
            userRepository.save(newUser);
        } catch (ConstraintViolationException ex) {
            throw new BusinessException(ex.getConstraintViolations().iterator().next().getMessage());
        }
        return newUser;
    }

    @Override
    public User registerStudent(UserDto userDto) {
        var newUser = register(userDto);
        newUser.setRoles(new HashSet<>(Set.of(roleService.findByName("PASIF"))));
        try {
            userRepository.save(newUser);
        } catch (ConstraintViolationException ex) {
            throw new BusinessException(ex.getConstraintViolations().iterator().next().getMessage());
        }
        return newUser;
    }


    @Override
    public User findOneByEmail(String email) {
        return userRepository.findOneByEmail(email)
                .orElseThrow(() -> new BusinessException(ResponseMessageEnum.BACK_USER_MSG_001));

    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseMessageEnum.BACK_USER_MSG_001));

    }




    @Override
    public Page<User> search(String searchText, String phoneNumber, Pageable pageable) {
//        return userRepository.search(searchText, phoneNumber, pageable);
        return null;
    }

    @Override
    public void changePassword(String currentClearTextPassword, String newPassword) {
        log.info("user: {}", SecurityUtils.getCurrentUsername());
        Optional.ofNullable(SecurityUtils.getCurrentUsername())
                .flatMap(userRepository::findOneByEmail)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    log.info("Kullanıcı şifresi değiştirildi. Kullanıcı: {}", user);
                });
    }

    @Override
    public void deactivateUser(Long userId) {
        User user = findById(userId);
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        User user = null;
        String phone = SecurityUtils.getCurrentUsername();
        log.info("getCurrentUser: "+phone);
        if (StringUtils.isNotEmpty(phone)) {
            user = userRepository.findOneByEmail(phone).orElse(null);
        }
        if (user == null) {
            throw new BusinessException(ResponseMessageEnum.BACK_USER_MSG_001);
        }
        return user;
    }

    @Override
    public User updateCurrentUser(UserDto userDto) {
        User user = getCurrentUser();
        User updatedUser = updateUser(user, userDto);
        userRepository.save(updatedUser);

        return updatedUser;
    }

    @Override
    public User updateUserByAdmin(Long userId, UserDto userDto) {
        User updatedUser;
        User user;
        try {
            user = findById(userId);
            updatedUser = updateUser(user, userDto);
            if (userDto.getRoles() != null) {
                List<Long> roleIds = userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList());
                updatedUser.setRoles(new HashSet<>(roleService.findAllById(roleIds)));
            }
            if (userDto.getActive() != null) {
                updatedUser.setActive(userDto.getActive());
            }

            userRepository.save(updatedUser);
//            log.info("Kullanıcı yönetici tarafından güncellendi: {}", updatedUser.getPhone());
        } catch (Exception e) {
            throw e;
        }
        return updatedUser;
    }

    @Override
    public void activateUser(Long userId) {
        User user = findById(userId);
        user.setActive(true);
        userRepository.save(user);
    }



    @Override
    public UserInformation userValidation(UserInformationDto dto) {
        if(StringUtils.isEmpty(dto.getFirstName())){
            throw  new BusinessException("First name must not be empty");
        }
        if(StringUtils.isEmpty(dto.getLastName())){
            throw  new BusinessException("LastName must not be empty");
        }
        if(StringUtils.isEmpty(dto.getTcNumber())){
            throw new BusinessException("Tc must not be empty");
        }
        if(StringUtils.isEmpty(dto.getPhone())){
            throw new BusinessException("Telephone numara must be empty");
        }
        return informationMapper.toEntity(dto);
    }

    @Override
    public UserInformation updateUserInformation(UserInformation infor,UserInformationDto dto) {
        if(!StringUtils.isEmpty(dto.getFirstName())){
            infor.setFirstName(dto.getFirstName());
        }
        if(!StringUtils.isEmpty(dto.getLastName())){
            infor.setLastName(dto.getLastName());
        }
        if(!StringUtils.isEmpty(dto.getPhone())){

            infor.setPhone(dto.getPhone());
        }
        if(ObjectUtils.isNotEmpty(dto.getAddress())){
            var address = new Address();
            if(!StringUtils.isEmpty(dto.getAddress().getCountry())){
                address.setCountry(dto.getAddress().getCountry());
            }
            if (!StringUtils.isEmpty(dto.getAddress().getState())){
                address.setState(dto.getAddress().getState());
            }
            if (!StringUtils.isEmpty(dto.getAddress().getCity())){
                address.setCity(dto.getAddress().getCity());
            }
            if (!StringUtils.isEmpty(dto.getAddress().getStreet())){
                address.setStreet(dto.getAddress().getStreet());
            }
            infor.setAddress(address);
        }

        return infor;
    }

    @Override
    public void deleteByEmail(String email) {
        var user=findOneByEmail(email);
        userRepository.deleteById(user.getId());
    }

    private User register(UserDto userDto){
        User newUser= new User();
        newUser.setActive(true);
        checkDuplicateEmail(userDto.getEmail());
        newUser.setEmail(userDto.getEmail().toLowerCase());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return newUser;

    }

    @Override
    public void assignRole(Long id, Role role) {
        var user = findById(id);
        user.setRoles(new HashSet<>(Set.of(role)));
        userRepository.save(user);
    }


    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new BusinessException(ResponseMessageEnum.BACK_USER_MSG_003);
        }
    }

    private User updateUser(User updatedUser ,UserDto userDto){
        if (StringUtils.isNotEmpty(userDto.getEmail())) {
            updatedUser.setEmail(userDto.getEmail());
        }
        return updatedUser;

    }

}
