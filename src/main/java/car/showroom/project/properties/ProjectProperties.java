package car.showroom.project.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "project")
@Setter
@Getter
public class ProjectProperties {
    private KeycloakProperties keycloak = new KeycloakProperties();
    private String allowedOrigin;
    @Getter
    @Setter
    public static class KeycloakProperties {

        private String adminUrl;
        private String realm;
        private String clientId;
        private String clientSecret;
        private String issuerUri;
    }

}
