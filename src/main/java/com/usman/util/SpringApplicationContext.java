package com.usman.util;


import com.usman.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Object getBeanByName(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static UserService getUserService() {
        return (UserService) applicationContext.getBean("userService");
    }

    public static String getActiveProfilesAsList() {
        return String.join(";", getActiveProfiles());
    }

    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }
}
