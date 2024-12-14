package maratik.budget_manager.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record UserSavingsAccountDto(
        UUID id,
        @NotNull @NotEmpty String name,
        @NotNull @Min(0) @Max(1) BigDecimal portion
) {
}
