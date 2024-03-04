package com.zalisoft.zalisoft.security.jwt;



import com.zalisoft.zalisoft.constant.SecurityConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Configuration
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {



    private final TokenProvider tokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
    }


    @Override
    public void afterPropertiesSet()  {

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthenticationToken(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            if (tokenProvider.validateToken(token)) {
                try {
                    return tokenProvider.getAuthentication(token);
                } catch (ExpiredJwtException exception) {
                    log.error("Request to parse expired JWT : {} failed", token, exception);
                } catch (UnsupportedJwtException exception) {
                    log.error("Request to parse unsupported JWT : {} failed", token, exception);
                } catch (MalformedJwtException exception) {
                    log.error("Request to parse invalid JWT : {} failed", token, exception);
                } catch (SignatureException exception) {
                    log.error("Request to parse JWT with invalid signature : {} failed", token, exception);
                } catch (IllegalArgumentException exception) {
                    log.error("Request to parse empty or null JWT : {} failed", token, exception);
                }
            }
        }
        return null;
    }
}
