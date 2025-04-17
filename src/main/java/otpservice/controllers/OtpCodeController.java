package otpservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otpservice.dto.OtpCodeRequest;
import otpservice.dto.OtpCodeResponse;
import otpservice.dto.OtpCodeValidationRequest;
import otpservice.models.User;
import otpservice.services.OtpCodeService;

import java.util.Optional;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpCodeController {

    private final OtpCodeService otpService;
    private final Logger log = LoggerFactory.getLogger(OtpCodeController.class);

    @PostMapping("/generate")
    public ResponseEntity<OtpCodeResponse> generate(
            @RequestBody(required = false) Optional<OtpCodeRequest> request, @AuthenticationPrincipal User user
    ) {
        log.info("Generating OTP code for user: {}", user.getId());
        log.debug("Generate OTP request details - operationId: {}, channel: {}, recipient: {}",
                request.map(OtpCodeRequest::getOperationId),
                request.map(OtpCodeRequest::getChannel),
                request.map(OtpCodeRequest::getRecipient));
        var response = otpService.generateCode(
                user.getId(),
                request.map(OtpCodeRequest::getOperationId).orElse(null),
                request.map(OtpCodeRequest::getChannel).orElse(null),
                request.map(OtpCodeRequest::getRecipient).orElse(null)
        );
        log.debug("OTP code generated successfully for user: {}", user.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validate(
            @RequestBody @Valid OtpCodeValidationRequest request,
            @AuthenticationPrincipal User user
    ) {
        log.info("Validating OTP code for user: {}, operationId: {}",
                user.getId(), request.getOperationId());
        log.debug("Validation request details - code: {}", request.getCode());
        otpService.validateOtp(user.getId(), request.getOperationId(), request.getCode());
        log.info("OTP code validated successfully for user: {}, operationId: {}",
                user.getId(), request.getOperationId());
        return ResponseEntity.ok().build();
    }
}