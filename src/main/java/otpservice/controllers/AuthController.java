package otpservice.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otpservice.dto.LoginRequest;
import otpservice.dto.RegisterRequest;
import otpservice.services.AuthService;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        log.info("Starting registration for user: {}", request.getUsername());
        authService.register(request);
        var response = new HashMap<String, String>();
        response.put("message", "User registered successfully");
        log.info("User registered successfully: {}", request.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info("Login attempt for user: {}", request.getUsername());
        var token = authService.login(request);
        var response = new HashMap<String, String>();
        response.put("message", "Login successful");
        response.put("token", token);
        log.info("User logged in successfully: {}", request.getUsername());
        return ResponseEntity.ok(response);
    }
}