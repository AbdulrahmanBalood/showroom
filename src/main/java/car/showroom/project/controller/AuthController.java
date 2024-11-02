package car.showroom.project.controller;

import car.showroom.project.dto.SignUpDto;
import car.showroom.project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/admin")
    public ResponseEntity<?> register(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.createAdminUser(signUpDto));
    }
    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.createUser(signUpDto));
    }
}
