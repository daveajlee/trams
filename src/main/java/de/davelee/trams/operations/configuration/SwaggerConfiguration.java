package de.davelee.trams.operations.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * This class configures swagger to ensure the correct api endpoints and information is shown.
 * @author Dave Lee
 */
@Configuration
@Profile("dev-test")
public class SwaggerConfiguration {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("TraMS Operations API")
                        .description("Spring shop sample application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://www.davelee.de")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

    /**
     * This method returns a GroupedOpenApi object containing the api information and endpoints.
     * @return a <code>GroupedOpenApi</code> object containing the api information and endpoints.
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("trams-operations")
                .pathsToMatch("/api/**")
                .build();
    }

}
