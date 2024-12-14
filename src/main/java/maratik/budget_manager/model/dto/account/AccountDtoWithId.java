package maratik.budget_manager.model.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AccountDtoWithId(
        UUID id,
        float proportion
) {
}
