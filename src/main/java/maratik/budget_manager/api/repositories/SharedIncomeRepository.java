package maratik.budget_manager.api.repositories;

import maratik.budget_manager.model.entities.SharedIncome;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface SharedIncomeRepository extends JpaRepository<SharedIncome, UUID> {

    @EntityGraph(attributePaths = {"savingsAccount"})
    @Query("select s from SharedIncome s where s.user.id = ?1 and s.savingsAccount.name in ?2")
    List<SharedIncome> findAllByUserIdAndNameSet_FetchSavingsAccount(UUID id, Collection<String> names);

    @EntityGraph(attributePaths = {"savingsAccount"})
    @Query("select s from SharedIncome s where s.user.id = ?1")
    List<SharedIncome> findAllByUserId_FetchSavingsAccount(UUID id);
}