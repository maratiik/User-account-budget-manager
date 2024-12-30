package maratik.budget_manager.model.mappers;

import maratik.budget_manager.model.dto.SharedIncomeDto;
import maratik.budget_manager.model.entities.SharedIncome;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface SharedIncomeMapper {

    @Mapping(source = "income.id", target = "incomeId")
    @Mapping(source = "savingsAccount.name", target = "userSavingsAccountName")
    SharedIncomeDto toDto(SharedIncome entity);
}
