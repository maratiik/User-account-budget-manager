package maratik.budget_manager.api.repositories;

import maratik.budget_manager.model.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, UUID> {

    @Query("select i from Income i where i.id = ?1 and i.user.id = ?2")
    Optional<Income> findByIdAndUserId(UUID id, UUID userId);

    @Query("select i from Income i where i.user.id = ?1")
    List<Income> findAllByUserId(UUID id);
}