package maratik.budget_manager.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SummaryDto(
        BigDecimal summaryIncome,
        Map<String, BigDecimal> sharedIncomeSummaries
) {
}
