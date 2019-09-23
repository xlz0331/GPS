package com.hwagain.framework.web.common.springfox.conf;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(ApiInfoProperties.class)
@Order(3)
public class SwaggerConfiguration {

	@Bean
	public Docket springfoxDocket(ApiInfoProperties apiInfo) {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.ignoredParameterTypes(ApiIgnore.class);
		docket.apiInfo(apiInfo(apiInfo));
		docket.pathMapping("/").select().paths(regex("^.*(?<!error)$")).build();
		// 下面这句代码是只生成被ApiOperation这个注解注解过的api接口
		// 以及最后一定要执行build()方法,不然不起作用
		docket.select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
		docket.globalOperationParameters(setHeaderToken());
		return docket;
	}

	@Bean
	ApiInfo apiInfo(ApiInfoProperties apiInfoProperties) {
		Contact contact = apiInfoProperties.getContact();
		ApiInfo apiInfo = new ApiInfo(apiInfoProperties.getTitle(), apiInfoProperties.getDescription(),
				apiInfoProperties.getVersion(), apiInfoProperties.getTermsOfServiceUrl(),
				new springfox.documentation.service.Contact(contact.getName(), contact.getUrl(), contact.getEmail()),
				apiInfoProperties.getLicense(), apiInfoProperties.getLicenseUrl());

		return apiInfo;
	}
	
	private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("X-Auth-Token").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
}
