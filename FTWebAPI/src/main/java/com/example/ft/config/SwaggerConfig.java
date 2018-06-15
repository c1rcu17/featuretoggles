package com.example.ft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Feature Toggles API")
                .description("A REST service that enables applications to have toggles to enable/disable features at run-time without deployment")
                .termsOfServiceUrl("")
                .contact(new Contact("Nuno Santos", "https://github.com/c1rcu17/featuretoggles", "eu.nuno@gmail.com"))
                .license("MIT")
                .licenseUrl("https://raw.githubusercontent.com/c1rcu17/featuretoggles/master/LICENSE")
                .version("1.0.0")
                .build();
    }

    private ResponseMessage getResponse(HttpStatus httpStatus) {
        ResponseMessageBuilder builder = new ResponseMessageBuilder()
                .code(httpStatus.value())
                .message(httpStatus.getReasonPhrase());
        return builder.build();
    }

    private List<ResponseMessage> getResponsesDelete() {
        return Arrays.asList(
                getResponse(HttpStatus.OK),
                getResponse(HttpStatus.NOT_FOUND),
                getResponse(HttpStatus.UNAUTHORIZED)
        );
    }

    private List<ResponseMessage> getResponsesGet() {
        return Arrays.asList(
                getResponse(HttpStatus.OK),
                getResponse(HttpStatus.UNAUTHORIZED)
        );
    }

    private List<ResponseMessage> getResponsesPatch() {
        return Arrays.asList(
                getResponse(HttpStatus.OK),
                getResponse(HttpStatus.BAD_REQUEST),
                getResponse(HttpStatus.NOT_FOUND),
                getResponse(HttpStatus.CONFLICT),
                getResponse(HttpStatus.UNAUTHORIZED)
        );
    }

    private List<ResponseMessage> getResponsesPost() {
        return Arrays.asList(
                getResponse(HttpStatus.CREATED),
                getResponse(HttpStatus.BAD_REQUEST),
                getResponse(HttpStatus.CONFLICT),
                getResponse(HttpStatus.UNAUTHORIZED)
        );
    }

    private List<ResponseMessage> getResponsesPut() {
        return Arrays.asList(
                getResponse(HttpStatus.OK),
                getResponse(HttpStatus.BAD_REQUEST),
                getResponse(HttpStatus.NOT_FOUND),
                getResponse(HttpStatus.CONFLICT),
                getResponse(HttpStatus.UNAUTHORIZED)
        );
    }

    @Bean
    public Docket productionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .securitySchemes(Arrays.asList(new ApiKey("Bearer", "Authorization", "Header")))
                .securityContexts(securityContext())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.DELETE, getResponsesDelete())
                .globalResponseMessage(RequestMethod.GET, getResponsesGet())
                .globalResponseMessage(RequestMethod.PATCH, getResponsesPatch())
                .globalResponseMessage(RequestMethod.POST, getResponsesPost())
                .globalResponseMessage(RequestMethod.PUT, getResponsesPut())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.ft"))
                .paths(PathSelectors.regex("/api/.*"))
                .build();
    }

    private List<SecurityContext> securityContext() {
        return Arrays.asList(SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference("Bearer",
                        new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")})))
                .forPaths(PathSelectors.regex("/api/(?!auth).*"))
                .build()
        );
    }

}
