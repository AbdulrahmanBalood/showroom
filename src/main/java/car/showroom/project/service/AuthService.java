package car.showroom.project.service;

import car.showroom.project.constants.RolesConstants;
import car.showroom.project.dto.SignUpDto;
import car.showroom.project.properties.ProjectProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Keycloak keycloak;
    private final ProjectProperties projectProperties;

    private final String clientId = "frontend";
    public String createAdminUser(SignUpDto signUpDto) {
        createUserOnKeyCloak(signUpDto,RolesConstants.ROLE_ADMIN);
        return "User created successfully";
    }
    public String createUser(SignUpDto signUpDto) {
        createUserOnKeyCloak(signUpDto, RolesConstants.ROLE_USER);
        return "User created successfully";
    }

    private void createUserOnKeyCloak(SignUpDto signUpDto,String role){

        UserRepresentation user = new UserRepresentation();
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setEnabled(true);

        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setTemporary(false);
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setValue(signUpDto.getPassword());
        user.setCredentials(Collections.singletonList(passwordCredential));
        validateUser(signUpDto.getUsername());

        try{
            Response response = keycloak.realm(projectProperties.getKeycloak().getRealm()).users().create(user);
            if (response.getStatus() != 201) {
                String responseBody = response.readEntity(String.class);
                throw new RuntimeException("Failed to create user. Status: " + responseBody);
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to create user", e);
        }

        assignRoleToUser(signUpDto.getUsername(),role);
    }
    private void validateUser(String username){
        List<UserRepresentation> existingUsers = keycloak.realm(projectProperties.getKeycloak().getRealm()).users().search(username);
        if (!existingUsers.isEmpty()) {
            throw new RuntimeException("User with this username already exists.");
        }
    }
    private void assignRoleToUser(String username,String role) {
        String userId = keycloak.realm(projectProperties.getKeycloak().getRealm()).users().search(username).get(0).getId();

        String clientUUID = keycloak.realm(projectProperties.getKeycloak().getRealm()).clients().findByClientId(clientId).get(0).getId();
        RoleRepresentation clientRole = keycloak.realm(projectProperties.getKeycloak().getRealm()).clients().get(clientUUID).roles().get(role).toRepresentation();

        keycloak.realm(projectProperties.getKeycloak().getRealm()).users().get(userId).roles().clientLevel(clientUUID).add(Collections.singletonList(clientRole));
    }
}
