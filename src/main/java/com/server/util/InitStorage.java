package com.server.util;

import com.server.services.ImageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitStorage {

    @Bean
    public CommandLineRunner initStorageImage(ImageService imageService) {
        return args -> {
            imageService.deleteAll();
            imageService.init();
        };
    }
}