package otpservice.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import otpservice.dto.UserResponse;
import otpservice.services.UserAdminService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserAdminController {

    private final UserAdminService userAdminService;
    private final Logger log = LoggerFactory.getLogger(UserAdminController.class);

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllNonAdminUsers() {
        log.info("Fetching all non-admin users");
        var users = userAdminService.getAllNonAdminUsers();
        log.debug("Found {} non-admin users", users.size());
        return ResponseEntity.ok(users);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        log.info("Deleting user with ID: {}", userId);
        userAdminService.deleteUserAndCodesById(userId);
        log.info("User {} deleted successfully", userId);
        return ResponseEntity.noContent().build();
    }
}