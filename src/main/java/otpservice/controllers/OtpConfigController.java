package otpservice.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otpservice.dto.OtpConfigRequest;
import otpservice.models.OtpConfig;
import otpservice.services.OtpConfigService;

@RestController
@RequestMapping("/api/admin/config")
@RequiredArgsConstructor
public class OtpConfigController {

    private final OtpConfigService service;
    private final Logger log = LoggerFactory.getLogger(OtpConfigController.class);

    @GetMapping
    public ResponseEntity<OtpConfig> getConfig() {
        log.info("Fetching current OTP configuration");
        var config = service.getCurrentConfig();
        log.debug("Current OTP config: {}", config);
        return ResponseEntity.ok(config);
    }

    @PatchMapping
    public ResponseEntity<OtpConfig> patchConfig(@RequestBody OtpConfigRequest request) {
        log.info("Updating OTP configuration");
        log.debug("Update request: {}", request);

        var updatedConfig = service.updateConfig(request);

        log.info("OTP configuration updated successfully");
        log.debug("Updated config: {}", updatedConfig);
        return ResponseEntity.ok(updatedConfig);
    }
}