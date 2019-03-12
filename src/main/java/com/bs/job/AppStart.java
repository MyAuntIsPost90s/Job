package com.bs.job;

import com.bs.job.service.common.util.LogUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AppStart extends SpringBootServletInitializer {

    public static void main(String[] args) {
        try {
            SpringApplication.run(AppStart.class, args);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AppStart.class);
    }

}
