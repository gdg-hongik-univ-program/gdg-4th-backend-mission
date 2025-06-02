package com.example.shopbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 API 경로 허용
                        .allowedOrigins("http://localhost:3000") // 프론트엔드 주소 (React 기준)
                        .allowedMethods("*") // GET, POST 등 모두 허용
                        .allowedHeaders("*"); // 모든 헤더 허용
            }
        };
    }
}
