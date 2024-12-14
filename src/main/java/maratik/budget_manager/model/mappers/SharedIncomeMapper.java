package maratik.budget_manager.model.mappers;

import maratik.budget_manager.api.repositories.UserRepository;
import maratik.budget_manager.model.dto.SharedIncomeDto;
import maratik.budget_manager.model.entities.SharedIncome;
import maratik.budget_manager.model.entities.User;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface SharedIncomeMapper {

    @Mapping(target = "user", qualifiedByName = "idToUser")
    SharedIncome toEntity(SharedIncomeDto dto, UUID userId,
                          @Context UserRepository userRepository);

    @Mapping(source = "income.id", target = "incomeId")
    @Mapping(source = "savingsAccount.name", target = "userSavingsAccountName")
    SharedIncomeDto toDto(SharedIncome entity);

    @Named("idToUser")
    default User idToUser(UUID userId,
                          @Context UserRepository userRepository) {
        return userRepository.getReferenceById(userId);
    }
}
