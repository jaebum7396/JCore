package jCore.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private String version = "V0.1";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("J-CORE API DOCUMENTATION")
                .version(version)
                .description("MEMBER API 문서")
                .contact(new Contact()
                        .name("주재범")
                        .email("jaebum7396@naver.com"));

        // Security 스키마 설정
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Security 요구사항 설정
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(info)
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", securityScheme))
                .addSecurityItem(securityRequirement)
                .components(new Components()
                        .addResponses("200", new ApiResponse().description("OK")
                                .content(new Content().addMediaType("application/json", new MediaType())))
                        .addResponses("400", new ApiResponse().description("잘못된 요청")
                                .content(new Content().addMediaType("application/json", new MediaType())))
                        .addResponses("500", new ApiResponse().description("서버 에러")
                                .content(new Content().addMediaType("application/json", new MediaType()))));
    }
}