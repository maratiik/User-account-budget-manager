package maratik.budget_manager.api.services;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.exceptions.EntityNotFoundException;
import maratik.budget_manager.api.repositories.IncomeRepository;
import maratik.budget_manager.api.repositories.SharedIncomeRepository;
import maratik.budget_manager.api.repositories.UserRepository;
import maratik.budget_manager.model.dto.IncomeDto;
import maratik.budget_manager.model.entities.Income;
import maratik.budget_manager.model.entities.SharedIncome;
import maratik.budget_manager.model.entities.UserSavingsAccount;
import maratik.budget_manager.model.mappers.IncomeMapper;
import maratik.budget_manager.model.mappers.SharedIncomeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeMapper incomeMapper;
    private final UserRepository userRepository;
    private final IncomeRepository incomeRepository;

    private final SharedIncomeRepository sharedIncomeRepository;
    private final SharedIncomeMapper sharedIncomeMapper;

    @Transactional
    public IncomeDto save(IncomeDto dto, UUID userId) {
        Income income = incomeRepository.save(
                incomeMapper.toEntity(dto, userId, userRepository)
        );
        List<UserSavingsAccount> userSavingsAccounts = income.getUser().getSavings();
        if (!userSavingsAccounts.isEmpty()) {
            income.setSharedIncomes(
                    sharedIncomeRepository.saveAll(
                            userSavingsAccounts.stream()
                                    .map(acc -> {
                                        SharedIncome sharedIncome = new SharedIncome();
                                        sharedIncome.setAmount(income.getAmount().multiply(acc.getPortion()));
                                        sharedIncome.setUser(income.getUser());
                                        sharedIncome.setIncome(income);
                                        sharedIncome.setSavingsAccount(acc);
                                        return sharedIncome;
                                    })
                                    .toList()
                    )
            );
        }
        return incomeMapper.toDto(incomeRepository.save(income), sharedIncomeMapper);
    }

    @Transactional
    public void delete(UUID userId, UUID incomeId) {
        Income income = incomeRepository.findByIdAndUserId(incomeId, userId).orElseThrow(() ->
                new EntityNotFoundException("Income not found."));
        incomeRepository.delete(income);
    }

    public List<IncomeDto> findAll(UUID userId) {
        return incomeRepository.findAllByUserId(userId).stream()
                .map(i -> incomeMapper.toDto(i, sharedIncomeMapper))
                .toList();
    }

    public IncomeDto findById(UUID userId, UUID incomeId) {
        return incomeMapper.toDto(
                incomeRepository.findByIdAndUserId(incomeId, userId).orElseThrow(() ->
                        new EntityNotFoundException("Income not found.")),
                sharedIncomeMapper
        );
    }
}
