package otpservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import otpservice.dao.OtpCodeRepository;
import otpservice.models.OtpCodeStatus;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtpExpirationScheduler {

    private final OtpCodeRepository repository;

    @Scheduled(fixedDelay = 60000)
    public void expireCodes() {
        var now = Instant.now();
        var expiredCodes = repository.findAllByStatusAndExpiresAtBefore(OtpCodeStatus.ACTIVE, now);

        if (!expiredCodes.isEmpty()) {
            log.info("Expired: {}", expiredCodes.size());
            expiredCodes.forEach(code -> code.setStatus(OtpCodeStatus.EXPIRED));
            repository.saveAll(expiredCodes);
        }
    }
}