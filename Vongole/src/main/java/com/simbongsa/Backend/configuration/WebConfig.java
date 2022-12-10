package com.simbongsa.Backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .exposedHeaders("Authorization")
                .exposedHeaders("*")    // todo : 필요한 헤더만 사용하도록 수정할것
                .allowedHeaders("*")
                .allowCredentials(true);
    }

//    @Override // 브라우저 콘솔 webjar 404에러 관련 시도 (해결 X)
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("/webjars/")
//                .resourceChain(false);
//        registry.setOrder(1);
//    }
}
