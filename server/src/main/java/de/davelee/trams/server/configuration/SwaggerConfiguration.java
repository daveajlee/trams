package de.davelee.trams.server.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
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

    /**
     * This method returns a OpenApi object containing the api information and endpoints.
     * @return a <code>OpenApi</code> object containing the api information and endpoints.
     */
    @Bean
    public OpenAPI tramsServerOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("TraMS Server API")
                        .description("TraMS Server is a part of the TraMS Platform which is responsible for managing the backend processes of a transport company including managing budgets, customer relationship, running and simulating the business.")
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
                .group("trams-server")
                .pathsToMatch("/api/**")
                .build();
    }


}

