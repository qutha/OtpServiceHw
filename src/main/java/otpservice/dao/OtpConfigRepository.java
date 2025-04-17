package otpservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import otpservice.models.OtpConfig;

import java.util.UUID;

public interface OtpConfigRepository extends JpaRepository<OtpConfig, UUID> {
}