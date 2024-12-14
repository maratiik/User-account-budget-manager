package maratik.budget_manager.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record SharedIncomeDto(
        UUID id,
        BigDecimal amount,
        UUID incomeId,
        String userSavingsAccountName
) {
}
