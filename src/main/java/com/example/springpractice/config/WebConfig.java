package com.example.springpractice.config;

import com.example.springpractice.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(tokenInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/"); // 여기에 제외할 path 지정
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.HEAD.name()
                )
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
