package otpservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import otpservice.dao.OtpCodeRepository;
import otpservice.dto.OtpCodeResponse;
import otpservice.models.ChannelType;
import otpservice.models.OtpCode;
import otpservice.models.OtpCodeStatus;
import otpservice.models.User;
import otpservice.services.channels.EmailOtpSender;
import otpservice.services.channels.FileOtpSender;
import otpservice.services.channels.SmsOtpSender;
import otpservice.services.channels.TelegramOtpSender;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpCodeService {

    private final OtpCodeRepository repository;
    private final OtpConfigService configService;
    private final EmailOtpSender emailOtpSender;
    private final SmsOtpSender smsOtpSender;
    private final TelegramOtpSender telegramOtpSender;
    private final FileOtpSender fileOtpSender;


    public OtpCodeResponse generateCode(
            UUID userId, UUID operationId, @Nullable ChannelType channelType, @Nullable String recipient
    ) {
        var config = configService.getCurrentConfig();
        var code = generateNumericCode(config.getCodeLength());

        var now = Instant.now();
        var expiresAt = now.plusSeconds(config.getLifetime());

        var otp = OtpCode
                .builder()
                .code(code)
                .status(OtpCodeStatus.ACTIVE)
                .createdAt(now)
                .expiresAt(expiresAt)
                .operationId(operationId)
                .user(User.builder().id(userId).build())
                .build();

        otp = repository.save(otp);

        switch (channelType) {
            case EMAIL -> emailOtpSender.send(otp.getCode(), recipient);
            case SMS -> smsOtpSender.send(otp.getCode(), recipient);
            case TELEGRAM -> telegramOtpSender.send(otp.getCode(), recipient);
            case null, default -> fileOtpSender.send(otp.getCode(), recipient);
        }

        return new OtpCodeResponse(otp.getId(), otp.getCode(), otp.getExpiresAt(), otp.getStatus());
    }

    private String generateNumericCode(int length) {
        var random = new SecureRandom();
        var sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @Transactional
    public void validateOtp(UUID userId, UUID operationId, String code) {
        var otp = repository
                .findByUserIdAndOperationIdAndCode(userId, operationId, code)
                .orElseThrow(() -> new IllegalArgumentException("Invalid code or operation"));
        if (otp.getStatus() != OtpCodeStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP code is not active");
        }

        if (otp.getExpiresAt().isBefore(Instant.now())) {
            otp.setStatus(OtpCodeStatus.EXPIRED);
            repository.save(otp);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP code is expired");
        }

        otp.setStatus(OtpCodeStatus.USED);
        repository.save(otp);
    }
}