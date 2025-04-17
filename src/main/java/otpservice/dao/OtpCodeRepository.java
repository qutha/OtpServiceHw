package otpservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import otpservice.models.OtpCode;
import otpservice.models.OtpCodeStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OtpCodeRepository extends JpaRepository<OtpCode, UUID> {
    List<OtpCode> findAllByStatusAndExpiresAtBefore(OtpCodeStatus status, Instant timestamp);

    void deleteAllByUserId(UUID userId);

    Optional<OtpCode> findByUserIdAndOperationIdAndCode(
            UUID userId,
            UUID operationId,
            String code
    );
}