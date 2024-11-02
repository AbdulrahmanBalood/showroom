package car.showroom.project.config;

import car.showroom.project.properties.ProjectProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ProjectProperties.class)
public class AppConfig {
}
