package lander.tawan.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Users CRUD")
                .version("1.0.0")
                .description("CRUD de usuários com soft delete, feito com Spring Boot 3 e PostgreSQL"));
    }
}