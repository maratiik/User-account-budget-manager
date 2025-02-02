package maratik.budget_manager.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SharedIncomeDto(
        UUID id,
        BigDecimal amount,
        UUID incomeId,
        String userSavingsAccountName
) {
}
