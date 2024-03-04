package com.zalisoft.zalisoft.service.impl;

import com.zalisoft.zalisoft.dto.*;
import com.zalisoft.zalisoft.enums.ResponseMessageEnum;
import com.zalisoft.zalisoft.exception.BusinessException;
import com.zalisoft.zalisoft.exception.InvalidPasswordException;
import com.zalisoft.zalisoft.mapper.UserInformationMapper;
import com.zalisoft.zalisoft.model.Personal;
import com.zalisoft.zalisoft.model.User;
import com.zalisoft.zalisoft.model.UserInformation;
import com.zalisoft.zalisoft.repository.UserRepository;
import com.zalisoft.zalisoft.service.RoleService;
import com.zalisoft.zalisoft.service.UserService;
import com.zalisoft.zalisoft.util.SecurityUtils;
import com.zalisoft.zalisoft.service.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
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
    public User registerPersonal( UserDto userDto) {
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
        log.info("user: {}",SecurityUtils.getCurrentUsername());
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
    public UserInformation updateUserInformation(UserInformationDto dto) {
        var infor= new UserInformation();
        if(!StringUtils.isEmpty(dto.getFirstName())){
            infor.setFirstName(dto.getFirstName());
        }
        if(!StringUtils.isEmpty(dto.getLastName())){
            infor.setLastName(dto.getLastName());
        }
        if(!StringUtils.isEmpty(dto.getPhone())){
            infor.setPhone(dto.getPhone());
        }
        var address=dto.getAddress();
        if(!ObjectUtils.isEmpty(address)){
            if(!StringUtils.isEmpty(address.getCity())){
                infor.getAddress().setCity(address.getCity());
            }
            if(!StringUtils.isEmpty(address.getState())){
                infor.getAddress().setState(address.getState());
            }
            if(!StringUtils.isEmpty(address.getStreet())){
                infor.getAddress().setStreet(address.getStreet());
            }
            if(!StringUtils.isEmpty(address.getPostalCode())){
                infor.getAddress().setPostalCode(address.getPostalCode());
            }
            if(!StringUtils.isEmpty(address.getCountry())){
                infor.getAddress().setCountry(address.getCountry());
            }
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
