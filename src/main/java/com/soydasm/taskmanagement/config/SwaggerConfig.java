package com.soydasm.taskmanagement.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Handles the application web configuration such as:
 * <ul>
 * <li>Registering the Spring Converters</li>
 *
 * <li>Swagger configuration</li>
 * </ul>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.soydasm.taskmanagement.controllers.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }



    private ApiInfo apiInfo() {
        return new ApiInfo(
                "TASKMANAGEMENT REST API",
                "REST API of communication services",
                "v1",
                "",
                new Contact("Mehmet Soydas", "www.mehmetsoydas.com", "soydas.mehmet@gmail.com"),
                "",
                "",
                new ArrayList<>());
    }

}

