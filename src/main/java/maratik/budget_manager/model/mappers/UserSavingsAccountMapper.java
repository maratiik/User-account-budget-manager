package maratik.budget_manager.model.mappers;

import maratik.budget_manager.api.repositories.UserRepository;
import maratik.budget_manager.model.dto.UserSavingsAccountDto;
import maratik.budget_manager.model.entities.User;
import maratik.budget_manager.model.entities.UserSavingsAccount;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserSavingsAccountMapper {

    UserSavingsAccount toEntity(UserSavingsAccountDto dto);

    UserSavingsAccountDto toDto(UserSavingsAccount account);
}
