package com.wzc.im.common.config;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author WANGZIC
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value(value = "${swagger.enabled}")
    Boolean swaggerEnabled;

    @Bean
    public Docket createProductRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("超哥通讯接口")
                .apiInfo(new ApiInfoBuilder().title("超哥通讯接口").version("1.0.0").description("超哥通讯功能开发接口文档").build())
                .select().apis(RequestHandlerSelectors.basePackage("com.wzc.im.controller"))
                .paths(PathSelectors.regex(".*/*/.*")).build();
    }
}