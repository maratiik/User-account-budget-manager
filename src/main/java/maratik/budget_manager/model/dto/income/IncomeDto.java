package maratik.budget_manager.model.dto.income;

import com.fasterxml.jackson.annotation.JsonInclude;
import maratik.budget_manager.model.dto.BaseIncomeDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record IncomeDto(
        LocalDate date,
        BigDecimal amount,
        String username
) implements BaseIncomeDto {
    @Override
    public BigDecimal getAmount() {
        return amount;
    }
}