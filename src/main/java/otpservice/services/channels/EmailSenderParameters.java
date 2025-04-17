package otpservice.services.channels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "email")
public class EmailSenderParameters {
    private String from;
    private String subject = "Your OTP Code";
}