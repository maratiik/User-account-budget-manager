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

    @Mapping(target = "user", qualifiedByName = "idToUser")
    UserSavingsAccount toEntity(UserSavingsAccountDto dto,
                                UUID userId,
                                @Context UserRepository userRepository);

    UserSavingsAccountDto toDto(UserSavingsAccount account);

    @Named("idToUser")
    default User idToUser(UUID userId, @Context UserRepository userRepository) {
        return userRepository.getReferenceById(userId);
    }
}
