package edu.infnet.lucasapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI lucasApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EncontreSeuPet API")
                        .description("API desenvolvida em **Spring Boot** como parte da **pós-graduação em Arquitetura de Software do Instituto Infnet**.  \n" +
                                "O sistema tem como objetivo auxiliar na **busca, registro e acompanhamento de pets desaparecidos**, conectando pessoas que perderam seus animais com quem os encontrou.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Lucas Esteves de Abreu Rodrigues")
                                .email("lucasesteves5304@gmail.com"))
                        .license(new License()
                                .url("http://springdoc.org")));
    }

    @Bean
    public OpenApiCustomizer swaggerPageableCustomizer() {
        return openApi -> {
            Components components = openApi.getComponents();
            Schema<?> pageableSchema = new Schema<>()
                    .addProperty("page", new IntegerSchema().example(0))
                    .addProperty("size", new IntegerSchema().example(10))
                    .addProperty("sort", new ArraySchema().items(new StringSchema().example("id,asc")));

            components.addSchemas("Pageable", pageableSchema);

            Parameter pageableParam = new Parameter()
                    .in("query")
                    .name("pageable")
                    .description("Parâmetros de paginação (opcional)")
                    .required(false)
                    .schema(pageableSchema);

            openApi.getComponents().addParameters("pageable", pageableParam);
        };
    }

}