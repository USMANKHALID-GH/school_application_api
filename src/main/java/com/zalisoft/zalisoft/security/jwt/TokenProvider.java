package com.zalisoft.zalisoft.security.jwt;



import com.zalisoft.zalisoft.constant.SecurityConstants;
import com.zalisoft.zalisoft.dto.AuthToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000L;
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private Key key;
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public AuthToken createToken(Authentication authentication) {
        List<String> privileges = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String authorities = privileges.stream().collect(Collectors.joining(","));

        Date validity = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY);

        String jwtTokenString = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(SecurityConstants.AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity).compact();
        return new AuthToken(jwtTokenString, validity.getTime(), privileges);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {

        if (token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            token = token.substring(7);
        }
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities;
        String authKey = claims.get(SecurityConstants.AUTHORITIES_KEY).toString();
        if (StringUtils.isNotEmpty(authKey)) {
            authorities = Arrays.stream(claims.get(SecurityConstants.AUTHORITIES_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } else {
            authorities = new ArrayList<>();
        }

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {

        if (authToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            authToken = authToken.substring(7);
        }
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature. jwtToken:{}", authToken);
            log.trace("Invalid JWT signature trace", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token. jwtToken:{}", authToken);
            log.trace("Expired JWT token trace", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token. jwtToken:{}", authToken);
            log.trace("Unsupported JWT token trace", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid. jwtToken:{}", authToken);
            log.trace("JWT token compact of handler are invalid trace", e);
        }
        return false;
    }
}