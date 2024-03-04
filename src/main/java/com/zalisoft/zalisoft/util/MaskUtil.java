package com.zalisoft.zalisoft.util;

import org.apache.commons.lang3.StringUtils;

public class MaskUtil {
    private static final int DEFAULT_MASK_LENGTH = 2;
    private static final int DEFAULT_MASKED_STR_LENGTH = 5;

    public static String mask(String str) {
        return mask(str, DEFAULT_MASK_LENGTH);
    }

    public static String mask(String str, int maskLength) {
        String stars = "";
        String maskedStr;
        if (StringUtils.isNotEmpty(str) && str.length() > maskLength) {
            for (int i = maskLength; i < DEFAULT_MASKED_STR_LENGTH; i++) {
                stars += "*";
            }
            maskedStr = str.substring(0, maskLength) + stars;
        } else {
            maskedStr = str + "***";
        }
        return maskedStr;
    }

}
