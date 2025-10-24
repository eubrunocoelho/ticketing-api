package com.eubrunocoelho.ticketing.config;

import com.eubrunocoelho.ticketing.web.interceptor.ScreenLabelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer
{
    private final ScreenLabelInterceptor screenLabelInterceptor;

    @Override
    public void addInterceptors( InterceptorRegistry registry )
    {
        registry.addInterceptor( screenLabelInterceptor );
    }
}
