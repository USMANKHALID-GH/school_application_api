package com.usman.constant;

public final class SecurityConstants {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORITIES_KEY = "auth";


    public static final String[] IGNORE_SWAGGER={"/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
            "/configuration/**", "/swagger-ui.html", "/webjars/**" ,
            "/v3/api-docs/**","/v3/**", "/swagger-ui/**"
            ,"/context-path/swagger-ui.html", "/context-path/v3/api-docs/**", "/context-path/swagger-ui/**"};
    public static final String[] IGNORE_OTHERS={"/test/**", "/content/**","/i18n/**",
            "/api/health-check/**", "/api/account"};

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
