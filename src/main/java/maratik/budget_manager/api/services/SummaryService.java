package maratik.budget_manager.api.services;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.repositories.SharedIncomeRepository;
import maratik.budget_manager.model.dto.IncomeDto;
import maratik.budget_manager.model.dto.SummaryDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final IncomeService incomeService;
    private final SharedIncomeRepository sharedIncomeRepository;
    private final SharedIncomeService sharedIncomeService;

    public SummaryDto getFullSummary(UUID userId) {
        BigDecimal totalIncome = incomeService.findAll(userId)
                .stream()
                .map(IncomeDto::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> sharedIncomes = new HashMap<>();
        this.getFullSharedIncomesMap(userId, sharedIncomes);
        return new SummaryDto(totalIncome, sharedIncomes);
    }

    public SummaryDto getIncomeSummary(UUID userId) {
        return new SummaryDto(
                incomeService.findAll(userId)
                        .stream()
                        .map(IncomeDto::amount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                null
        );
    }

    public SummaryDto getSharedIncomeSummary(UUID userId, Collection<String> savingsAccountNames) {
        Map<String, BigDecimal> sharedIncomes = new HashMap<>();
        if (savingsAccountNames == null || savingsAccountNames.isEmpty()) {
            this.getFullSharedIncomesMap(userId, sharedIncomes);
        } else {
            this.getFullSharedIncomesMap(userId, sharedIncomes, savingsAccountNames);
        }
        return new SummaryDto(
                null,
                sharedIncomes
        );
    }

    private void getFullSharedIncomesMap(UUID userId,
                                         Map<String, BigDecimal> sharedIncomes) {
        sharedIncomeService.findAllByUserId(userId)
                .forEach(i -> {
                    if (sharedIncomes.containsKey(i.userSavingsAccountName())) {
                        sharedIncomes.put(
                                i.userSavingsAccountName(),
                                sharedIncomes.get(i.userSavingsAccountName())
                                        .add(i.amount()));
                    } else {
                        sharedIncomes.put(i.userSavingsAccountName(), i.amount());
                    }
                });
    }

    private void getFullSharedIncomesMap(UUID userId,
                                         Map<String, BigDecimal> sharedIncomes,
                                         Collection<String> savingsAccountNames) {
        sharedIncomeService.findAllByUserIdAndNameSet(userId, savingsAccountNames)
                .forEach(i -> {
                    if (sharedIncomes.containsKey(i.userSavingsAccountName())) {
                        sharedIncomes.put(
                                i.userSavingsAccountName(),
                                sharedIncomes.get(i.userSavingsAccountName())
                                        .add(i.amount()));
                    } else {
                        sharedIncomes.put(i.userSavingsAccountName(), i.amount());
                    }
                });
    }
}
