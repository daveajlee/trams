package de.davelee.trams.crm.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class configures swagger to ensure the correct api endpoints and information is shown.
 * @author Dave Lee
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI tramsCrmOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("TraMS CRM API")
                        .description("TraMS CRM is a part of the TraMS Platform which is responsible for managing the customer relationship with the transport company.")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("https://www.davelee.de")))
                .externalDocs(new ExternalDocumentation()
                        .description("TraMS Documentation")
                        .url("https://www.davelee.de/trams/"));
    }

    /**
     * This method returns a GroupedOpenApi object containing the api information and endpoints.
     * @return a <code>GroupedOpenApi</code> object containing the api information and endpoints.
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("trams-crm")
                .pathsToMatch("/api/**")
                .build();
    }

}
