package com.service.pichincha.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class PichinchaServiceConfiguration {

    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Bean
    public ModelMapper modelMapperWithoutStrict() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

}
