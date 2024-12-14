package maratik.budget_manager.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record IncomeDto(
        UUID id,
        LocalDate date,
        @NotNull BigDecimal amount,
        List<SharedIncomeDto> sharedIncomes
) {
}
