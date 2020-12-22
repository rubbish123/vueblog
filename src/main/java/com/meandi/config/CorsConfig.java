package com.meandi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//解决跨域问题
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") //springboot2.4这样写，不然报错
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONs")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
