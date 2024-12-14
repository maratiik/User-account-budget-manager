package maratik.budget_manager.api.repositories;

import maratik.budget_manager.model.entities.TotalAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TotalAmountRepository extends JpaRepository<TotalAmount, UUID> {

    @Query("select t from TotalAmount t where t.user.id = ?1 and t.name is not null")
    List<TotalAmount> findAllByUserIdNameNotNull(UUID id);

    @Query("select t from TotalAmount t where t.user.id = ?1 and t.name is null")
    Optional<TotalAmount> findByUserIdNameNull(UUID id);

    @Query("select t from TotalAmount t where t.user.id = ?1 and t.name = ?2")
    Optional<TotalAmount> findByUserIdAndName(UUID id, String name);

    @Query("select t from TotalAmount t where t.user.id = ?1 and t.name is null")
    List<TotalAmount> findAllByUserIdNameNull(UUID id);

    @Query("select t from TotalAmount t where t.user.id = ?1 and t.name in ?2")
    List<TotalAmount> findAllByUserIdAndNameSet(UUID id, Collection<String> names);

    @Query("select t from TotalAmount t where t.user.id = ?1")
    List<TotalAmount> findAllByUserId(UUID id);
}