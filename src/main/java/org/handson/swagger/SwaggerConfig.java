package org.handson.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
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

	@Value("${apiInfo.description}")
	private String description;
	@Value("${apiInfo.contact.name}")
	private String contactName;
	@Value("${apiInfo.contact.url}")
	private String contactUrl;
	@Value("${apiInfo.contact.email}")
	private String contactEmail;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).useDefaultResponseMessages(true).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.ant("/v1/persons/**")).build()
				.genericModelSubstitutes(ResponseEntity.class);
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact(contactName, contactUrl, contactEmail);
		ApiInfoBuilder apibuilder = new ApiInfoBuilder();
		apibuilder.contact(contact);
		apibuilder.description(description);
		return apibuilder.build();
	}
}
