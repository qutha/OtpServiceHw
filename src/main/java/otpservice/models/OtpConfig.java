package otpservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "otp_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OtpConfig {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private int codeLength;

    @Column(nullable = false)
    private int lifetime;
}