package otpservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import otpservice.models.Role;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private Role role;
}