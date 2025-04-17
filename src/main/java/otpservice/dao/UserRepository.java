package otpservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import otpservice.models.Role;
import otpservice.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    boolean existsByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.role <> 'ADMIN'")
    List<User> findAllNonAdminUsers();
}