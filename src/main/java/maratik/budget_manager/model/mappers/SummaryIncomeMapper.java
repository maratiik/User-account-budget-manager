package maratik.budget_manager.model.mappers;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.model.dto.account.AccountDto;
import maratik.budget_manager.model.dto.income.IncomeDto;
import maratik.budget_manager.model.dto.summary.FullSummaryIncomeDto;
import maratik.budget_manager.model.entities.TotalAmount;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SummaryIncomeMapper {

    private final IncomeMapper incomeMapper;
    private final AccountMapper accountMapper;

    public FullSummaryIncomeDto toSummaryDto(Collection<TotalAmount> totalIncomes) throws LazyInitializationException {
        IncomeDto income = null;
        List<AccountDto> accounts = new ArrayList<>();
        String username = null;
        for (TotalAmount inc : totalIncomes) {
            if (inc.getName() == null || inc.getName().isEmpty()) {
                income = incomeMapper.toSummaryAccountDto(inc);
            } else {
                accounts.add(accountMapper.toSummaryAccountDto(inc));
            }
            if (inc.getUser().getUsername() != null && !inc.getUser().getUsername().isEmpty()) {
                username = inc.getUser().getUsername();
            }
        }
        return new FullSummaryIncomeDto(
                income,
                accounts,
                username
        );
    }
}
