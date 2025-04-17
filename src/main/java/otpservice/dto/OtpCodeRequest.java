package otpservice.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import otpservice.models.ChannelType;

import java.util.UUID;

@Data
public class OtpCodeRequest {
    @Nullable
    private UUID operationId;

    @Nullable
    private ChannelType channel;

    @Nullable
    private String recipient;
}