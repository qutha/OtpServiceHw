package otpservice.services.channels;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramOtpSender implements OtpSender {
    private final TelegramParameters properties;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void send(String code, String recipient) {
        var url = String.format("https://api.telegram.org/bot%s/sendMessage", properties.getToken());

        var message = "Ваш OTP-код: %s".formatted(code);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestJson = String.format(
                "{\"chat_id\":\"%s\", \"text\":\"%s\"}",
                recipient, message
        );

        var request = new HttpEntity<>(requestJson, headers);

        try {
            var response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully sent message Telegram OTP");
            } else {
                log.error("Error sending Telegram OTP: {}", response.getBody());
            }
        } catch (Exception e) {
            log.error("Error OTP Telegram Sender", e);
        }
    }
}