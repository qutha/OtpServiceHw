package otpservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otpservice.dao.OtpConfigRepository;
import otpservice.dto.OtpConfigRequest;
import otpservice.models.OtpConfig;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpConfigService {

    private final OtpConfigRepository repository;

    public Optional<OtpConfig> findCurrentConfig() {
        return repository.findAll().stream().findFirst();
    }

    public OtpConfig getCurrentConfig() {
        return findCurrentConfig()
                .orElseThrow(() -> new IllegalStateException("OTP config not initialized"));
    }

    public OtpConfig updateConfig(OtpConfigRequest request) {
        var configs = repository.findAll();
        var config = configs.isEmpty() ? new OtpConfig() : configs.getFirst();

        if (request.getCodeLength() != null) {
            config.setCodeLength(request.getCodeLength());
        }
        if (request.getLifetime() != null) {
            config.setLifetime(request.getLifetime());
        }

        return repository.save(config);
    }
}