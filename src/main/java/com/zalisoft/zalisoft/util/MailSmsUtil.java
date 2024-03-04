package com.zalisoft.zalisoft.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Map;


public class MailSmsUtil {
    private MailSmsUtil() {
    }

    public static String processTemplate(String mailTemplate, Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            mailTemplate = mailTemplate.replace(entry.getKey(), entry.getValue());
        }
        return mailTemplate;
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static String formatGsm(String gsm) {
        gsm = gsm.replaceAll("[^0-9]", "");
        if (gsm.startsWith("0")) {
            gsm = gsm.replaceFirst("0", "");
        }
        return gsm;
    }

}
