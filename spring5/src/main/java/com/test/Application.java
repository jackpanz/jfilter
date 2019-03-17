package com.test;

import com.bj.json.spring5.JFilterHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@ComponentScan({"com.bj.json","com.test"})
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
    public JFilterHttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        JFilterHttpMessageConverter messageConverter = new JFilterHttpMessageConverter(objectMapper);
        return messageConverter;
    }

}
