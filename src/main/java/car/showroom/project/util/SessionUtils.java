package car.showroom.project.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;
@Slf4j
@RequiredArgsConstructor
public class SessionUtils {
    public static Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }
    public static String getCurrentUser() {
        String username;
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("SecurityContextHolder is empty");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) principal;
            AccessToken token = keycloakPrincipal.getKeycloakSecurityContext().getToken();
            username = token.getPreferredUsername();
        } else if (principal instanceof String) {
            username = (String) principal;
        } else {
            username = "";
        }
        return username;
    }
}
