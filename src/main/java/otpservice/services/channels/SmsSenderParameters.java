package otpservice.services.channels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "smpp")
public class SmsSenderParameters {
    private String host;
    private int port;
    private String systemId;
    private String password;
    private String systemType;
    private String sourceAddr;
}