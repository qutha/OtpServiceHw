package otpservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otpservice.dao.OtpCodeRepository;
import otpservice.dao.UserRepository;
import otpservice.dto.UserResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;
    private final OtpCodeRepository otpCodeRepository;

    public List<UserResponse> getAllNonAdminUsers() {
        return userRepository.findAllNonAdminUsers().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getRole()))
                .toList();
    }

    @Transactional
    public void deleteUserAndCodesById(UUID userId) {
        otpCodeRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);
    }

}