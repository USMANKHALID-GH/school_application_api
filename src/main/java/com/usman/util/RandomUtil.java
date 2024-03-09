package com.usman.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomUtil {

    private static final int DEF_COUNT = 8;

    private static AtomicInteger integer = new AtomicInteger();

    private RandomUtil() {
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    public static String generateKey() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }


    public static String randomAlphanumeric() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    public static String randomNumberic() {
        return RandomStringUtils.randomNumeric(10).concat(String.valueOf(((Double) (Math.random() * 10000)).intValue()));
    }

    public static String generateEncodeKey(String generatedKey) {
        return Base64.getEncoder().encodeToString(generatedKey.getBytes());
    }

    public static String decodeString(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    public  static  String  generateStudentNumber(){
        int year= LocalDateTime.now().getYear();
        String random=String.valueOf(getRandomNumber("10","99"));
        String sixDigitNumber = String.format("%06d", integer.get());
        return  year+random+sixDigitNumber;
    }

    public static int getRandomNumber() {
        int min = Integer.parseInt("100000");
        int max = Integer.parseInt("999999");
        Random foo = new Random();
        int randomNumber = foo.nextInt(max - min) + min;
        if (randomNumber == min) {
            return min + 1;
        } else {
            return randomNumber;
        }
    }

    public static int getRandomNumber(String mi, String ma) {
        int min = Integer.parseInt(mi);
        int max = Integer.parseInt(ma);
        Random foo = new Random();
        int randomNumber = foo.nextInt(max - min) + min;
        if (randomNumber == min) {
            return min + 1;
        } else {
            return randomNumber;
        }
    }
}
