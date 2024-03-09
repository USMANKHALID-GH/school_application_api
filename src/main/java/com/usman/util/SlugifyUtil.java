package com.usman.util;

import com.github.slugify.Slugify;

import java.util.Locale;

public class SlugifyUtil {

    private static final Slugify slugify = Slugify.builder().locale(Locale.forLanguageTag("tr-TR")).build();

    public static String slugify(String str) {
        return str != null ? slugify.slugify(str.toLowerCase()) : null;
    }

    public static String slugifyWithSuffix(String str, Long suffix) {
        return str != null ? slugify(str) + "-" + suffix : null;
    }
}
