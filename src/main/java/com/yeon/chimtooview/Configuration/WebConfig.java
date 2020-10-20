package com.yeon.chimtooview.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://chimtooview.net").allowedOrigins("http://chimtooview.net/rughj2v9uiefj2q9eifjowefj"); // cors 에러 시 이거는 고정!
    }
}