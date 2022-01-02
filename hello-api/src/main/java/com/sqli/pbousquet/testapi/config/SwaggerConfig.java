package com.sqli.pbousquet.testapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.*;

@Configuration
public class SwaggerConfig {

    private String basePath="/";

    private static final Collection<VendorExtension> VENDOR_EXTENSIONS = new ArrayList<>();

    private static final Contact DEFAULT_CONTACT = new Contact(
            "Philippe Bousquet", "http://sqli.com", "pbousquet@sqli.com");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "TestAPI", "Test API pour WS Swagger / OpenAPI", "0.0.5",
            "http://localhost:8080/swagger-ui.html", DEFAULT_CONTACT,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", VENDOR_EXTENSIONS);

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Arrays.asList("application/json"));

    @Bean
    public Docket api(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return basePath;
                    }
                })
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .select().paths(PathSelectors.ant("/api/**")).build();
    }


}
