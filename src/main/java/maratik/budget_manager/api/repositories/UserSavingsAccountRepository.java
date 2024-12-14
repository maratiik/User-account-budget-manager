package maratik.budget_manager.api.repositories;

import maratik.budget_manager.model.entities.UserSavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserSavingsAccountRepository extends JpaRepository<UserSavingsAccount, UUID> {

    @Query("select u from UserSavingsAccount u where u.user.id = ?1")
    List<UserSavingsAccount> findAllByUserId(UUID id);
}