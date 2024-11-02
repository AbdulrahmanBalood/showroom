package car.showroom.project.config;

import car.showroom.project.properties.ProjectProperties;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.keycloak.admin.client.Keycloak;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {
    private final ProjectProperties projectProperties;
    @Bean
    public Keycloak keycloakAdmin() {
        return KeycloakBuilder.builder()
                .serverUrl(projectProperties.getKeycloak().getAdminUrl())
                .realm(projectProperties.getKeycloak().getRealm())  // Use the "showroom" realm here
                .clientId(projectProperties.getKeycloak().getClientId())
                .clientSecret(projectProperties.getKeycloak().getClientSecret())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}
