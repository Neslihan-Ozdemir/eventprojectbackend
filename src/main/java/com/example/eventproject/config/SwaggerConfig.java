package com.example.eventproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.apache.poi.sl.usermodel.PresetColor.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Etkinlik Planlama Uygulaması API")
                        .description("Etkinlik Planlama Uygulaması backend API dokumantasyonu")
                        .version("v1")
                );
    }
}
