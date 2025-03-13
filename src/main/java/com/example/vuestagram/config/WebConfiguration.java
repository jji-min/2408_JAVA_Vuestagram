package com.example.vuestagram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 설정파일이라는 의미
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // RestController의 모든 URL에 "/api" Prefix를 설정
        configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class));
        // 추가할 prefix는 "/api", 어노테이션이 RestController인 것만 핸들링해서 "/api"를 앞에 붙이겠다는 설정
    }
}
