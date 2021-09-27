package com.cassandra.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Api")
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.cassandra.example.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Customer registration")
                .description("Customer_Details_Application")
                .termsOfServiceUrl("http://customer.com")
                .contact("customer@customer.in").license("Customer application license")
                .licenseUrl("customer").version("2.0").build();
    }
}
