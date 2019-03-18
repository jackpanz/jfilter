package com.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jackpanz.json.spring5.JFilterHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@Configuration
@SpringBootApplication
@ComponentScan({"com.github.jackpanz.json","com.test"})
public class Application extends SpringBootServletInitializer implements WebMvcConfigurer {

    /**
     * 发布到TOMCAT后模拟web.xml
     * main启动不需要
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }

    @Order(0)
    @Bean
    public JFilterHttpMessageConverter jFilterHttpMessageConverter(ObjectMapper objectMapper) {
        JFilterHttpMessageConverter messageConverter = new JFilterHttpMessageConverter(objectMapper);
        return messageConverter;
    }

}
