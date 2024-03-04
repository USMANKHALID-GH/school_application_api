package com.zalisoft.zalisoft;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT1H")
@EnableAsync
public class SchoolApi {

    public static void main(String[] args) {
        SpringApplication.run(SchoolApi.class, args);
    }

}
