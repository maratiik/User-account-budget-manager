package maratik.budget_manager.api.services;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.repositories.SharedIncomeRepository;
import maratik.budget_manager.model.dto.SharedIncomeDto;
import maratik.budget_manager.model.entities.SharedIncome;
import maratik.budget_manager.model.mappers.SharedIncomeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SharedIncomeService {

    private final SharedIncomeRepository sharedIncomeRepository;
    private final SharedIncomeMapper sharedIncomeMapper;

    @Transactional
    public void saveAll(Collection<SharedIncome> incomes) {
        sharedIncomeRepository.saveAll(incomes);
    }

    public List<SharedIncomeDto> findAllByUserId(UUID userId) {
        return sharedIncomeRepository.findAllByUserId_FetchSavingsAccount(userId)
                .stream()
                .map(sharedIncomeMapper::toDto)
                .toList();
    }

    public List<SharedIncomeDto> findAllByUserIdAndNameSet(UUID userId,
                                                           Collection<String> nameSet) {
        return sharedIncomeRepository.findAllByUserIdAndNameSet_FetchSavingsAccount(userId, nameSet)
                .stream()
                .map(sharedIncomeMapper::toDto)
                .toList();
    }
}
