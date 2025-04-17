package otpservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OtpCodeValidationRequest {
    @NotNull
    private UUID operationId;

    @NotBlank
    private String code;
}