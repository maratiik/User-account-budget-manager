package maratik.budget_manager.api.repositories;

import maratik.budget_manager.model.entities.SharedIncome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SharedIncomeRepository extends JpaRepository<SharedIncome, UUID> {
}