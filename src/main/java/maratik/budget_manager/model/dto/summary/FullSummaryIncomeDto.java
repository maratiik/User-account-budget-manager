package maratik.budget_manager.model.dto.summary;

import com.fasterxml.jackson.annotation.JsonInclude;
import maratik.budget_manager.model.dto.account.AccountDto;
import maratik.budget_manager.model.dto.income.IncomeDto;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FullSummaryIncomeDto(
    IncomeDto totalIncome,
    List<AccountDto> totalAccounts,
    String username
) {
}
