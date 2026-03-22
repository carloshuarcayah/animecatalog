package pe.com.carlosh.animecatalog.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Esto obliga a Swagger a usar la URL de Railway con HTTPS
        Server productionServer = new Server();
        productionServer.setUrl("https://animecatalog-production.up.railway.app");
        productionServer.setDescription("Servidor de Producción en Railway");

        return new OpenAPI().servers(List.of(productionServer));
    }
}