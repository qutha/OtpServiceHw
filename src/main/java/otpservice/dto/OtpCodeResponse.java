package otpservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import otpservice.models.OtpCodeStatus;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OtpCodeResponse {
    private UUID id;
    private String code;
    private Instant expiresAt;
    private OtpCodeStatus status;
}