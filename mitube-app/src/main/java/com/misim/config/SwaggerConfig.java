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
                new Tag().name("유저 API").description("유저 정보 관련 API")
        );

        return GroupedOpenApi.builder()
                .group("유저 API")
                .pathsToMatch("/users/**")
                .addOpenApiCustomizer(openApi -> openApi.setTags(tags))
                .build();
    }

    @Bean
    public GroupedOpenApi termGroup() {
        List<Tag> tags = List.of(
                new Tag().name("약관 API").description("약관 정보 제공 API")
        );

        return GroupedOpenApi.builder()
                .group("약관 API")
                .pathsToMatch("/terms/**")
                .addOpenApiCustomizer(openApi -> openApi.setTags(tags))
                .build();
    }

    @Bean
    public GroupedOpenApi videoGroup() {
        List<Tag> tags = List.of(
                new Tag().name("동영상 API").description("동영상 정보 관련 API")
        );

        return GroupedOpenApi.builder()
                .group("동영상 API")
                .pathsToMatch("/videos/**")
                .addOpenApiCustomizer(openApi -> openApi.setTags(tags))
                .build();
    }
}
