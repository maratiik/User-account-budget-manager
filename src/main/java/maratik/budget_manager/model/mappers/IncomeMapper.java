package maratik.budget_manager.model.mappers;

import maratik.budget_manager.api.repositories.UserRepository;
import maratik.budget_manager.model.dto.IncomeDto;
import maratik.budget_manager.model.dto.SharedIncomeDto;
import maratik.budget_manager.model.entities.Income;
import maratik.budget_manager.model.entities.SharedIncome;
import maratik.budget_manager.model.entities.User;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface IncomeMapper {

    @Mapping(target = "user", qualifiedByName = "idToUser")
    Income toEntity(IncomeDto dto,
                    @Context UUID userId,
                    @Context UserRepository userRepo);

    @Mapping(source = "sharedIncomes", target = "sharedIncomes", qualifiedByName = "mapSharedIncomes")
    IncomeDto toDto(Income entity,
                    @Context SharedIncomeMapper sharedIncomeMapper);

    @Named("idToUser")
    default User idToUser(UUID id,
                          @Context UserRepository userRepo) {
        return userRepo.getReferenceById(id);
    }

    @Named("mapSharedIncomes")
    default List<SharedIncomeDto> mapSharedIncomes(List<SharedIncome> sharedIncomes,
                                                   @Context SharedIncomeMapper sharedIncomeMapper) {
        return sharedIncomes.stream()
                .map(sharedIncomeMapper::toDto)
                .toList();
    }
}
