package car.showroom.project.config;

import car.showroom.project.config.security.KeycloakJwtTokenConverter;
import car.showroom.project.config.security.TokenConverterProperties;
import car.showroom.project.properties.ProjectProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;
    private final ProjectProperties projectProperties;
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String REALM_CLAIM = "roles";
    private static final String GROUPS = "groups";

    public SecurityConfig (TokenConverterProperties tokenConverterProperties, ProjectProperties projectProperties){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        this.keycloakJwtTokenConverter = new KeycloakJwtTokenConverter(jwtGrantedAuthoritiesConverter, tokenConverterProperties);
        this.projectProperties = projectProperties;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Public endpoint
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(keycloakJwtTokenConverter)
                        )
                )
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(projectProperties.getAllowedOrigin());
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = projectProperties.getKeycloak().getIssuerUri();
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }
    @Bean
    GrantedAuthoritiesMapper userAuthoritiesMapperForKeyCloak() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            var authority = authorities.iterator().next();
            boolean isOidc = authority instanceof OidcUserAuthority;

            if(isOidc){
                var oidcUserAuthority = (OidcUserAuthority) authority;
                var userInfo = oidcUserAuthority.getUserInfo();

                if(userInfo.hasClaim(REALM_ACCESS_CLAIM)){
                    var realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM);
                    var roles = (Collection<String>) realmAccess.get(REALM_ACCESS_CLAIM);
                    mappedAuthorities.addAll(grantedAuthoritiesFromClaim(roles));
                }
            else if(userInfo.hasClaim(GROUPS)){
                Collection<String> groups = userInfo.getClaimAsStringList(GROUPS);
                mappedAuthorities.addAll(grantedAuthoritiesFromClaim(groups));
            }
            } else {
                var oauth2UserAuthority = (OidcUserAuthority) authority;
                Map<String,Object> userAttributes = oauth2UserAuthority.getAttributes();
                if(userAttributes.containsKey(REALM_ACCESS_CLAIM)){
                    Map<String,Object> realmAccess = (Map<String, Object>) userAttributes.get(REALM_ACCESS_CLAIM);
                    Collection<String> roles = (Collection<String>) realmAccess.get(REALM_CLAIM);
                    mappedAuthorities.addAll(grantedAuthoritiesFromClaim(roles));
                }
            }
            return mappedAuthorities;
        };
    }
    Collection<GrantedAuthority> grantedAuthoritiesFromClaim(Collection<String> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());

    }
}
