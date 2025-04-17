package otpservice.services.channels;

public interface OtpSender {
    void send(String code, String recipient);
}