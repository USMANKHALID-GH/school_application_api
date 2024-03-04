package com.zalisoft.zalisoft.util;


import com.zalisoft.zalisoft.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Slf4j
public class SecurityUtils {

    private static final String KEY = "LAAS-ASENCKEY";

    public static void main(String[] args) {
        String text = "Test";
        log.info("text: {}", text);
        String encryptedText = SecurityUtils.encrypt(text);
        log.info("encryptedText: {}", encryptedText);
        String decryptedText = SecurityUtils.decrypt(encryptedText);
        log.info("decryptedText: {}", decryptedText);
    }

    public static String getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Optional<String> username = Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetails) {
                        UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                        return springSecurityUser.getUsername();
                    } else if (authentication.getPrincipal() instanceof String) {
                        return (String) authentication.getPrincipal();
                    }
                    return null;
                });
        return username.orElse(null);
    }

    public static boolean hasAuthority(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
                .orElse(false);
    }

    public static void userIdControl(Long headerUserId, Long userId) {
        log.info("User bilgisi headerUserId :" + headerUserId + "userId :" + userId);
        if (!userId.equals(headerUserId)) {
            log.info(" eşleşme true user:" + userId);
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "Size ait olmayan kullanıcı ile işlem yapamazsınız.");
        }
    }

    public static String encrypt(String text) {
        String ecryptedText = "";
        try {
            byte[] key = KEY.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            ecryptedText = Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Pasword Decrypte hatası.");
        }
        return ecryptedText;
    }

    public static String decrypt(String encryptedText) {
        String decryptedText = "";
        try {
            byte[] key = KEY.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedText = new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));

        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Pasword Decrypte hatası.");
        }
        return decryptedText;
    }
}
