package com.christoffer.apiimages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Legacy AI API",description = "API para criacao de um Chat com inteeligencia artificial", version = "1.5"))
public class ApiImagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiImagesApplication.class, args);
    }

}
