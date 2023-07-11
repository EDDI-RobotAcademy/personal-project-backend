package kr.eddi.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://127.0.0.1:8080",
                        "http://localhost:8080",
                        "http://13.125.154.146:8080",
                        "http://127.0.0.1:8000",
                        "http://localhost:8000",
                        "http://13.125.154.146:8000"
                        )
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
