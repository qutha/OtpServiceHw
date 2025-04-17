package otpservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import otpservice.dao.UserRepository;
import otpservice.dto.LoginRequest;
import otpservice.dto.RegisterRequest;
import otpservice.models.Role;
import otpservice.models.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {
        var adminExists = userRepository.existsByRole(Role.ADMIN);
        var role = adminExists ? Role.USER : Role.ADMIN;

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return jwtService.generateToken(user);
    }
}