package com.test.kafkaTest;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @Description: swagger配置
 * @Author: zxy
 * @Date: 下午3:15 2018/6/19
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket commonDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("kafka")
                .apiInfo(new ApiInfoBuilder().title("kafka测试接口").build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.test.kafkaTest.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }

}
