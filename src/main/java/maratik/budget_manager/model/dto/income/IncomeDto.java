package maratik.budget_manager.model.dto.income;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record IncomeDto(
        LocalDate date,
        BigDecimal amount,
        String username
) {}