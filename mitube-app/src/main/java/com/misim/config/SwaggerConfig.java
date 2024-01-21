package com.misim.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Mitube Api")
                .description("Youtube 클론 프로젝트")
                .version("1.0.0");
    }

    @Bean
    public GroupedOpenApi userGroup() {
        List<Tag> tags = List.of(
                new Tag().name("User API").description("User API")
        );

        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch("/users/**")
                .addOpenApiCustomizer(openApi -> openApi.setTags(tags))
                .build();
    }
}
