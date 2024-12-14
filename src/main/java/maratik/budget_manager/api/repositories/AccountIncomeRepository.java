package maratik.budget_manager.api.repositories;

import maratik.budget_manager.model.dto.account.NameProportionDto;
import maratik.budget_manager.model.entities.AccountIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface AccountIncomeRepository extends JpaRepository<AccountIncome, UUID> {

    @Query("select distinct a.name, a.proportion from AccountIncome a where a.user.id = ?1")
    Set<NameProportionDto> findAccountNamesByUserId(UUID id);

    @Query("select a from AccountIncome a where a.id = ?1 and a.user.id = ?2")
    Optional<AccountIncome> findByIdAndUserId(UUID id, UUID userId);

    @Query("select a from AccountIncome a where a.user.id = ?1")
    List<AccountIncome> findAllByUserId(UUID id);

    @Query("select a from AccountIncome a where a.user.id = ?1 and a.name in ?2")
    List<AccountIncome> findAllByUserIdAndAndNameSet(UUID id, Collection<String> names);

    @Query("select a from AccountIncome a where a.name = ?1")
    List<AccountIncome> findByName(String name);
}