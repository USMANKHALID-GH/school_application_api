package com.usman.service.impl;


import com.usman.enums.ResponseMessageEnum;
import com.usman.exception.BusinessException;
import com.usman.model.User;
import com.usman.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component("userAuthService")
public class UserAuthServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private final UserService userService;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        log.info("Authenticating {}", email);
        User user = userService.findOneByEmail(email);
        if (!user.getActive()) {
            throw new BusinessException(ResponseMessageEnum.BACK_USER_MSG_004);
        }
        return createSpringSecurityUser(user);
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        List<GrantedAuthority> grantedAuthorities = user.getPrivileges().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }


}