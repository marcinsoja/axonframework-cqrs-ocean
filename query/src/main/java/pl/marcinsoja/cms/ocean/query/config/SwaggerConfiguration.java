package pl.marcinsoja.cms.ocean.query.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Instant;
import java.util.Collections;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2).directModelSubstitute(Instant.class, String.class)
                                    .select()
                                    .apis(RequestHandlerSelectors.any())
                                    .paths(getPaths())
                                    .build()
                                    .pathMapping("/")
                                    .apiInfo(apiInfo());
    }

    private Predicate<String> getPaths() {
        return Predicates.or(
                PathSelectors.ant("/pages/**")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Ocean Query API",
                "",
                "0.0.1",
                "",
                new Contact("Ocean", "https://github.com/marcinsoja/axonframework-cqrs-ocean", "dev@marcinsoja.pl"),
                "",
                "",
                Collections.emptyList());
    }
}
