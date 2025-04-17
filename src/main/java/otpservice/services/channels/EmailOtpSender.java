package otpservice.services.channels;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component("email")
@RequiredArgsConstructor
public class EmailOtpSender implements OtpSender {

    private final JavaMailSender mailSender;
    private final EmailSenderParameters props;

    @Override
    public void send(String code, String recipient) {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);
            helper.setTo(recipient);
            helper.setSubject(props.getSubject());
            helper.setFrom(props.getFrom());
            helper.setText("Your code: %s".formatted(code));
            mailSender.send(message);
            log.info("Email sent to {}", recipient);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
        }
    }
}