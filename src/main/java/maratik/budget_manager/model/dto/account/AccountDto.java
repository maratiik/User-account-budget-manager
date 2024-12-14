package maratik.budget_manager.model.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import maratik.budget_manager.model.dto.BaseIncomeDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AccountDto(
    String name,
    LocalDate date,
    BigDecimal amount,
    float proportion,
    String username
) implements BaseIncomeDto {
    @Override
    public BigDecimal getAmount() {
        return amount;
    }
}
