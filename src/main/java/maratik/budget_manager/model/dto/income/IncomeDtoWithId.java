package maratik.budget_manager.model.dto.income;

import java.math.BigDecimal;
import java.util.UUID;

public record IncomeDtoWithId(
        UUID id,
        BigDecimal amount
) {
}
