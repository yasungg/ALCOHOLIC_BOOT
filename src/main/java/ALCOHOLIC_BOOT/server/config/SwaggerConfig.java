package ALCOHOLIC_BOOT.server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api(@Value("${springdoc.swagger-ui.version}") String springdocVersion) {
        Info info = new Info()
                .version(springdocVersion)
                .title("Mashilang")
                .description("Mashilang API Document");

        return new OpenAPI().info(info);

    }
}
