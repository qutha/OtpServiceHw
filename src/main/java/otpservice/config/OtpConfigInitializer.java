package otpservice.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import otpservice.dao.OtpConfigRepository;
import otpservice.models.OtpConfig;

@Component
@RequiredArgsConstructor
public class OtpConfigInitializer {

    private final OtpConfigRepository repository;

    @PostConstruct
    public void ensureConfigExists() {
        if (repository.count() == 0) {
            OtpConfig defaultConfig = new OtpConfig();
            defaultConfig.setCodeLength(6);
            defaultConfig.setLifetime(300);
            repository.save(defaultConfig);
        }
    }
}