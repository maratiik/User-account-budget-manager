package maratik.budget_manager.api.repositories;

import maratik.budget_manager.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from User u where u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.fingerprint = ?1")
    Optional<User> findByFingerprint(String fingerprint);
}