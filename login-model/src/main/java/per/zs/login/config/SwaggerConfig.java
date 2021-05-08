package per.zs.login.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import per.zs.common.beans.constants.Constant;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** 
* Create time 2020年5月9日 下午6:17:21 
* @author sheng.zhong 
* @Description  
*/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		// 添加header参数
//		ParameterBuilder ticketPar = new ParameterBuilder();
//		List<Parameter> pars = new ArrayList<>();
//		ticketPar.name(Constant.COOKIE_KEY_STR).description("token令牌信息").modelRef(new ModelRef("string")).parameterType("header")
//				.required(false).build(); //
//		pars.add(ticketPar.build());
		return new Docket(DocumentationType.SWAGGER_2)
		        //分组名,不指定默认为default
		        .groupName("登录模块")
		        .apiInfo(getApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("per.zs.login.controller")).paths(PathSelectors.any())
				.build()
				.securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
//				.globalOperationParameters(pars);
	}

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("API接口文档")
                .description("登录模块接口文档")
                .version("1.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }
    
    private List<ApiKey> securitySchemes() {
        return Arrays.asList(
                new ApiKey("Authorization", Constant.COOKIE_KEY_STR, "header"));
    }

    private List<SecurityContext> securityContexts() {
        return Arrays.asList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(
                new SecurityReference("Authorization", authorizationScopes));
    }
}
