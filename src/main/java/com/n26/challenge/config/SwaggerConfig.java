package com.n26.challenge.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig { 
	
    @Bean
    public Docket api() {    	
        return new Docket(DocumentationType.SWAGGER_2)
        		.useDefaultResponseMessages(false)
        		.select()                                  
        		.apis(RequestHandlerSelectors.any())              
        		.paths(PathSelectors.any())
        		.build()
        		.apiInfo(apiInfo())
        		.select() 
        		.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
        		.build();
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfo("N26 Coding Challenge", 
    			null, null, null,
    			new Contact("Andy Tran", "http://tranandy.com", "andytran@aggienetwork.com"), 
    			null, null, Collections.emptyList());
    }
    
}
