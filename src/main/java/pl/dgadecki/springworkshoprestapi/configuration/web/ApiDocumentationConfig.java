package pl.dgadecki.springworkshoprestapi.configuration.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApiDocumentationConfig {

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact()
                .name("Damian Gadecki")
                .email("damian.gadecki@vm.pl");

        License license = new License()
                .name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0.html");

        Info info = new Info()
                .title("Workshop")
                .description("API offered by the Workshop")
                .version("1.0.0")
                .license(license)
                .contact(contact);

        List<Server> servers = List.of(
                new Server().url("http://localhost:8789").description("Local"),
                new Server().url("https://workshop-dev.com").description("Development"),
                new Server().url("https://workshop.com").description("Production")
        );

        return new OpenAPI().info(info).servers(servers);
    }

    @Bean
    public GroupedOpenApi articlesOpenApi() {
        return GroupedOpenApi.builder()
                .group("Articles")
                .pathsToMatch("/api/v1/articles/**")
                .build();
    }

    @Bean
    public GroupedOpenApi customersOpenApi() {
        return GroupedOpenApi.builder()
                .group("Customers")
                .pathsToMatch("/api/v1/customers/**")
                .build();
    }
}
