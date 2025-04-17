package otpservice.dto;

import lombok.Data;

@Data
public class OtpConfigRequest {
    private Integer codeLength;
    private Integer lifetime;
}