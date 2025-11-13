package com.eubrunocoelho.ticketing.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig
{
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );

    @Bean
    public ObjectMapper objectMapper( Jackson2ObjectMapperBuilder builder )
    {
        return builder
                .modules(
                        javaTimeModule(),
                        hibernate6Module()
                )
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
                )
                .serializationInclusion(
                        JsonInclude.Include.NON_NULL
                )
                .build();
    }

    @Bean
    public JavaTimeModule javaTimeModule()
    {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule
                .addSerializer(
                        LocalDateTime.class,
                        new LocalDateTimeSerializer(
                                DATE_TIME_FORMATTER
                        )
                );

        return javaTimeModule;
    }

    @Bean
    public Hibernate6Module hibernate6Module()
    {
        Hibernate6Module hibernate6Module = new Hibernate6Module();

        hibernate6Module.disable(
                Hibernate6Module.Feature.USE_TRANSIENT_ANNOTATION
        );

        hibernate6Module.enable(
                Hibernate6Module.Feature.FORCE_LAZY_LOADING
        );

        return hibernate6Module;
    }
}
