package com.umlimi.robotapocalypse.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author MUNASHE MURIMI
 * @created 19/1/2023
 **/
@Configuration
public class BeanConfigs {
    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
