package car.showroom.project.config.security;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakJwtTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {
    private static final String RESOURCE_ACCESS = "resource_access";
    private static final String ROLES = "roles";
    private static final String ROLE_PREFIX = "ROLE_";

    private final TokenConverterProperties properties;
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
    public KeycloakJwtTokenConverter(JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter,TokenConverterProperties properties) {
        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
        this.properties = properties;
    }
    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt jwt) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        Map<String, Object> resourceAccess = jwt.getClaim(RESOURCE_ACCESS);
        if(resourceAccess != null){
            Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId());
            if(resource != null){
                Collection<String> roles = (Collection<String>) resource.get(ROLES);
                if(roles != null){
                    roles.forEach(role ->{
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                }
            }
        }
        authorities.addAll(jwtGrantedAuthoritiesConverter.convert(jwt));
        String principalClaimName = properties.getPrincipalAttribute()
                .map(jwt::getClaimAsString)
                .orElse(jwt.getClaimAsString(JwtClaimNames.SUB));
        return new JwtAuthenticationToken(jwt, authorities, principalClaimName);

    }

}

