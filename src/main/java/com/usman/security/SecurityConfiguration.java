package com.usman.security;

import com.usman.security.jwt.JwtAuthenticationEntryPoint;
import com.usman.security.jwt.JwtAuthorizationFilter;
import com.usman.security.jwt.TokenProvider;
import com.usman.constant.PrivilegeConstant;
import com.usman.constant.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;


import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {


    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private AuthenticationConfiguration configuration;

    @Autowired
    private TokenProvider tokenProvider;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.cors(withDefaults()).csrf(csrf->csrf.disable());

        http .authorizeHttpRequests(
                        auth->{
                         auth.requestMatchers("/api/**","/api/auth/login","/api/public/**").permitAll();
                         auth.requestMatchers("/api/super/**","/api/all/**").hasAuthority(PrivilegeConstant.SUPER);
                         auth.requestMatchers("/api/admin/**","/api/all/**").hasAuthority(PrivilegeConstant.ADMIN);
                         auth.requestMatchers("/api/passive/**").hasAuthority(PrivilegeConstant.PASIF);
                         auth.requestMatchers("/api/student/**").hasAuthority(PrivilegeConstant.STUDENT);
                         auth.anyRequest().authenticated();
                        })
                .exceptionHandling(exception->exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(new JwtAuthorizationFilter(configuration.getAuthenticationManager(),tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session ->session.sessionCreationPolicy(STATELESS));

        return  http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> {
            web.ignoring().requestMatchers(HttpMethod.OPTIONS, "/**");
            web.ignoring().requestMatchers(SecurityConstants.IGNORE_SWAGGER);
            web.ignoring().requestMatchers( SecurityConstants.IGNORE_OTHERS);
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManagerProvider(UserDetailsService detailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(detailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

}