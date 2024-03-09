package com.usman.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;

public class LogUtil {
    public static void logBatchProcess(ProceedingJoinPoint joinPoint) {
        ThreadContext.clearAll();
        ThreadContext.put("processId", RandomUtil.randomNumberic());
        ThreadContext.put("activeProfiles", SpringApplicationContext.getActiveProfilesAsList());
        ThreadContext.put("batchProcess", Boolean.toString(true));
        String[] classPackage = joinPoint.getSignature().getDeclaringTypeName().split("\\.");
        ThreadContext.put("batchName", classPackage[classPackage.length - 1]);
    }

    public static void logAPIProcess(String requestURL, String userEmail) {
        ThreadContext.put("processId", RandomUtil.randomNumberic());
        ThreadContext.put("activeProfiles", SpringApplicationContext.getActiveProfilesAsList());
        ThreadContext.put("batchProcess", Boolean.toString(false));
        ThreadContext.put("requestURL", requestURL);
        ThreadContext.put("userEmail", userEmail);
    }

    public static boolean isBatchProcess() {
        String batchProcess = ThreadContext.get("batchProcess");
        return !StringUtils.isEmpty(batchProcess) && Boolean.parseBoolean(batchProcess);
    }

    public static String batchName() {
        return ThreadContext.get("batchName");
    }
}
